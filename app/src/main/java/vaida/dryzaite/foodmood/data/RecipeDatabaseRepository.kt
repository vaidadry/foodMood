package vaida.dryzaite.foodmood.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import vaida.dryzaite.foodmood.database.ApiRecipesPagingSource
import vaida.dryzaite.foodmood.database.RecipeDao
import vaida.dryzaite.foodmood.database.RecipeDatabase
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.asDomainModel
import vaida.dryzaite.foodmood.network.ExternalRecipe
import vaida.dryzaite.foodmood.network.RecipeApiService
import vaida.dryzaite.foodmood.network.asDatabaseModel



//Repository integrated with coroutines to send work off main thread
class RecipeDatabaseRepository(application: Application, private val service: RecipeApiService) : RecipeRepository {


    private val recipeDao: RecipeDao?

    private val allRecipes: LiveData<List<RecipeEntry>>

    init {
        val database = RecipeDatabase.getInstance(application)
        recipeDao = database.recipeDao
        allRecipes = recipeDao.getAllRecipes()
    }


    //    When we get the recipes, we are already returning LiveData in Dao.
//    So we don’t need to get the data from background thread.
    override fun getAllRecipes(): LiveData<List<RecipeEntry>> = allRecipes


    override suspend fun insertRecipe(recipe: RecipeEntry) {
        recipeDao?.insertRecipe(recipe)
    }


    override suspend fun deleteRecipe(recipe: RecipeEntry) {
        recipeDao?.deleteRecipe(recipe)
    }


    override suspend fun updateRecipe(recipe: RecipeEntry) {
        recipeDao?.updateRecipe(recipe)
    }


    override fun getRecipeWithId(key: String): LiveData<RecipeEntry> {
        return recipeDao!!.getRecipeWithId(key)
    }

    override fun getFavorites(): LiveData<List<RecipeEntry>> {
        return recipeDao!!.getFavorites()
    }

    override fun getFilteredRecipes(meal: Int): LiveData<List<RecipeEntry>> {
        return  recipeDao!!.getFilteredRecipes(meal)
    }


    // search method run on flow coroutines and paging library
    override fun searchExternalRecipes(searchQuery: String?): Flow<PagingData<ExternalRecipe>> {
        Timber.i("searchExternalRecipes initiated")
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE),
            pagingSourceFactory = { ApiRecipesPagingSource(service, searchQuery)}
        ).flow

    }


    companion object {
        private const val NETWORK_PAGE_SIZE = 10
    }
}