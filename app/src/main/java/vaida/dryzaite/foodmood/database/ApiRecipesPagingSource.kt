package vaida.dryzaite.foodmood.database

import androidx.paging.PagingSource
import retrofit2.HttpException
import retrofit2.await
import timber.log.Timber
import vaida.dryzaite.foodmood.network.ExternalRecipe
import vaida.dryzaite.foodmood.network.RecipeApiService
import java.io.IOException

private const val API_STARTING_PAGE_INDEX = 1

//PagingSource implementation defines the source of data and how to retrieve data from that source
class ApiRecipesPagingSource(
    private val service: RecipeApiService,
    private val query: String? = ""
) : PagingSource<Int, ExternalRecipe>() {

    //load func triggers async load, avoids multiple requests same time, in-memory cache,
    // keeps track of page to be requested
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ExternalRecipe> {
        val position = params.key ?: API_STARTING_PAGE_INDEX

        return try {
            val response = service.getRecipesAsync(query, position)
            val recipes = response.await().results
            Timber.i("load recipes: ${recipes.size}")
            LoadResult.Page(
                data = recipes,
                prevKey = if (position == API_STARTING_PAGE_INDEX) null else position -1,
                nextKey = if(recipes.isEmpty()) null else position +1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}