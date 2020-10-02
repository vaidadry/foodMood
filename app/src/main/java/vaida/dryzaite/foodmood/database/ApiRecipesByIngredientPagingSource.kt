package vaida.dryzaite.foodmood.database

import androidx.paging.PagingSource
import retrofit2.HttpException
import retrofit2.await
import timber.log.Timber
import vaida.dryzaite.foodmood.network.ExternalRecipe
import vaida.dryzaite.foodmood.network.RecipeApiService
import vaida.dryzaite.foodmood.utilities.API_STARTING_PAGE_INDEX
import java.io.IOException


class ApiRecipesByIngredientPagingSource(
    private val service: RecipeApiService,
    private val query: String? = ""
) : PagingSource<Int, ExternalRecipe>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ExternalRecipe> {
        val page = params.key ?: API_STARTING_PAGE_INDEX

        return try {
            val response = service.getRecipesByIngredientAsync(query, page)
            val recipes = response.await().results
            Timber.i("load recipes: ${recipes.size}")
            LoadResult.Page(
                data = recipes,
                prevKey = if (page == API_STARTING_PAGE_INDEX) null else page - 1,
                nextKey = if (recipes.isEmpty()) null else page + 1
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            return LoadResult.Error(exception)
        }
    }
}