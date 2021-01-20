package vaida.dryzaite.foodmood.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.network.ExternalRecipe

// REPOSITORY is mediator between database and viewModel
interface RecipeRepository {
    suspend fun insertRecipe(recipe: RecipeEntry)
    fun getAllRecipes(): LiveData<List<RecipeEntry>>
    suspend fun deleteRecipe(recipe: RecipeEntry)
    suspend fun updateRecipe(recipe: RecipeEntry)
    fun getRecipeWithId(key: String): LiveData<RecipeEntry>
    fun getFavorites(): LiveData<List<RecipeEntry>>
    fun getFilteredRecipes(meal: Int): LiveData<List<RecipeEntry>>
    fun searchExternalRecipes(searchQuery: String): Flow<PagingData<ExternalRecipe>>
    fun searchExternalRecipesByIngredient(queryList: List<String>): Flow<PagingData<ExternalRecipe>>

//    fun getCachedRecipes(queryString: String): PagingSource<Int, CacheRecipeEntry>
//    suspend fun insertCachedRecipes(recipes: List<CacheRecipeEntry>)
//    suspend fun clearCachedRecipes()
}