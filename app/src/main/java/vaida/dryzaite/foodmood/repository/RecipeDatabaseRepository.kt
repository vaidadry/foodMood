package vaida.dryzaite.foodmood.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import vaida.dryzaite.foodmood.database.ApiRecipesByIngredientPagingSource
import vaida.dryzaite.foodmood.database.ApiRecipesPagingSource
import vaida.dryzaite.foodmood.database.RecipeDao
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.network.ExternalRecipe
import vaida.dryzaite.foodmood.network.RecipeApiService
import vaida.dryzaite.foodmood.utilities.NETWORK_PAGE_SIZE
import javax.inject.Inject

// Repository integrated with coroutines to send work off main thread
class RecipeDatabaseRepository @Inject constructor(
    private val recipeDao: RecipeDao,
    private val service: RecipeApiService
) : RecipeRepository {

    private val allRecipes: LiveData<List<RecipeEntry>> = recipeDao.getAllRecipes()

    // When we get the recipes, we are already returning LiveData in Dao.
    // So we don’t need to get the data from background thread.
    override fun getAllRecipes(): LiveData<List<RecipeEntry>> = allRecipes

    override suspend fun insertRecipe(recipe: RecipeEntry) {
        recipeDao.insertRecipe(recipe)
    }

    override suspend fun deleteRecipe(recipe: RecipeEntry) {
        recipeDao.deleteRecipe(recipe)
    }

    override suspend fun updateRecipe(recipe: RecipeEntry) {
        recipeDao.updateRecipe(recipe)
    }

    override fun getRecipeWithId(key: String): LiveData<RecipeEntry> {
        return recipeDao.getRecipeWithId(key)
    }

    override fun getFavorites(): LiveData<List<RecipeEntry>> {
        return recipeDao.getFavorites()
    }

    override fun getFilteredRecipes(meal: Int): LiveData<List<RecipeEntry>> {
        return recipeDao.getFilteredRecipes(meal)
    }

    // search method run on flow coroutines and paging library
    override fun searchExternalRecipes(searchQuery: String): Flow<PagingData<ExternalRecipe>> {
        Timber.i("searchExternalRecipes initiated: $searchQuery")
        // appending '%' so we can allow other characters to be before and after the query string
        Timber.i("dbQuery made: $searchQuery")

        val pagingSourceFactory = { ApiRecipesPagingSource(service, searchQuery) }

        // TO FETCH DB + NETWORK (BUGS in API currently- reloading multiple times, crashing etc, so im using only network above)
        // val pagingSourceFactory =  { database.externalRecipesDao().getExternalRecipes(dbQuery) }
        // also excluding REMOTE MEDIATOR bellow, coz it blocks loading states and disable footer

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 2,
                initialLoadSize = 10),
//            remoteMediator = ExternalRecipesRemoteMediator(
//                dbQuery,
//                service,
//                database
//            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }

    override fun searchExternalRecipesByIngredient(queryList: List<String>): Flow<PagingData<ExternalRecipe>> {
        Timber.i("searchExternalRecipes initiated: $queryList")

        val dbQuery = queryList.joinToString(",")

        Timber.i("dbQuery made: $dbQuery")

        val pagingSourceFactory = { ApiRecipesByIngredientPagingSource(service, dbQuery) }
        // TO FETCH DB + NETWORK (BUGS in API currently- reloading multiple times, crashing etc, so im using only network above)
        // val pagingSourceFactory =  { database.externalRecipesDao().getExternalRecipes(dbQuery) }
        // also excluding REMOTE MEDIATOR bellow, coz it blocks loading states and disable footer

        return Pager(
            config = PagingConfig(
                pageSize = NETWORK_PAGE_SIZE,
                enablePlaceholders = false,
                prefetchDistance = 2,
                initialLoadSize = 10),
//            remoteMediator = ExternalRecipesRemoteMediator(
//                dbQuery,
//                service,
//                database
//            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}