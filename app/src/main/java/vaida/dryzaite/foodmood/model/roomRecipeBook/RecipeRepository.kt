package vaida.dryzaite.foodmood.model.roomRecipeBook

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.network.ExternalRecipe


//REPO is mediator between database and viewModel
interface RecipeRepository {
    suspend fun insertRecipe(recipe: RecipeEntry)
    fun getAllRecipes(): LiveData<List<RecipeEntry>>
    suspend fun deleteRecipe(recipe: RecipeEntry)
    suspend fun updateRecipe(recipe: RecipeEntry)
    fun getRecipeWithId(key: String): LiveData<RecipeEntry>
    fun getFavorites(): LiveData<List<RecipeEntry>>
    fun getFilteredRecipes(meal: Int): LiveData<List<RecipeEntry>>
    suspend fun refreshExternalRecipes()
    suspend fun searchExternalRecipes(searchQuery: String?)
    val results: LiveData<List<ExternalRecipe>>
}