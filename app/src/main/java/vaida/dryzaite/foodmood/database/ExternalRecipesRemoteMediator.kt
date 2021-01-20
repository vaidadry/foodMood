package vaida.dryzaite.foodmood.database

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import retrofit2.HttpException
import retrofit2.await
import vaida.dryzaite.foodmood.model.RemoteKeys
import vaida.dryzaite.foodmood.network.ExternalRecipe
import vaida.dryzaite.foodmood.network.RecipeApiService
import vaida.dryzaite.foodmood.utilities.API_STARTING_PAGE_INDEX
import java.io.IOException
import java.io.InvalidObjectException

@OptIn(ExperimentalPagingApi::class)
class ExternalRecipesRemoteMediator(
    private val query: String,
    private val service: RecipeApiService,
    private val recipeDatabase: RecipeDatabase
) : RemoteMediator<Int, ExternalRecipe>() {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, ExternalRecipe>): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getRemoteKeyClosestToCurrentPosition(state)
                remoteKeys?.nextKey?.minus(1) ?: API_STARTING_PAGE_INDEX
            }
            LoadType.PREPEND -> {
                val remoteKeys = getRemoteKeyForFirstItem(state)
                    ?: return MediatorResult.Error(InvalidObjectException("Remote key and the prevKey should not be null"))
                // If the previous key is null, then we can't request more data
                val prevKey = remoteKeys.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
                remoteKeys.prevKey
            }
            LoadType.APPEND -> {
                val remoteKeys = getRemoteKeyForLastItem(state)
                if (remoteKeys?.nextKey == null) {
                    return MediatorResult.Error(InvalidObjectException("Remote key should not be null for $loadType"))
                }
                remoteKeys.nextKey
            }
        }

        try {
            val apiResponse = service.getRecipesAsync(query, page)

            val recipes = apiResponse.await().results
            val endOfPaginationReached = recipes.isEmpty()
            recipeDatabase.withTransaction {
                // clear all tables in the database
                if (loadType == LoadType.REFRESH) {
                    recipeDatabase.remoteKeysDao().clearRemoteKeys()
                    recipeDatabase.externalRecipesDao().clearExternalRecipes()
                }
                val prevKey = if (page == API_STARTING_PAGE_INDEX) null else page - 1
                val nextKey = if (endOfPaginationReached) null else page + 1
                val keys = recipes.map {
                    RemoteKeys(recipeHref = it.href, prevKey = prevKey, nextKey = nextKey)
                }
                recipeDatabase.remoteKeysDao().insertAll(keys)
                recipeDatabase.externalRecipesDao().insertExternalRecipes(recipes)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: IOException) {
            return MediatorResult.Error(exception)
        } catch (exception: HttpException) {
            return MediatorResult.Error(exception)
        }
    }

    private suspend fun getRemoteKeyForLastItem(state: PagingState<Int, ExternalRecipe>): RemoteKeys? {
        // Get the last page that was retrieved, that contained items.
        // From that last page, get the last item
        return state.pages.lastOrNull() { it.data.isNotEmpty() }?.data?.lastOrNull()
            ?.let { recipe ->
                // Get the remote keys of the last item retrieved
                recipeDatabase.remoteKeysDao().remoteKeysRecipeHref(recipe.href)
            }
    }

    private suspend fun getRemoteKeyForFirstItem(state: PagingState<Int, ExternalRecipe>): RemoteKeys? {
        // Get the first page that was retrieved, that contained items.
        // From that first page, get the first item
        return state.pages.firstOrNull { it.data.isNotEmpty() }?.data?.firstOrNull()
            ?.let { recipe ->
                // Get the remote keys of the first items retrieved
                recipeDatabase.remoteKeysDao().remoteKeysRecipeHref(recipe.href)
            }
    }

    private suspend fun getRemoteKeyClosestToCurrentPosition(
        state: PagingState<Int, ExternalRecipe>
    ): RemoteKeys? {
        // The paging library is trying to load data after the anchor position
        // Get the item closest to the anchor position
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)?.href?.let { recipeHref ->
                recipeDatabase.remoteKeysDao().remoteKeysRecipeHref(recipeHref)
            }
        }
    }
}