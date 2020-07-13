package vaida.dryzaite.foodmood.model.room

import androidx.lifecycle.LiveData
import vaida.dryzaite.foodmood.model.RecipeEntry


//REPO is mediator between database and viewModel
interface RecipeRepository {
    fun insertRecipe(recipe: RecipeEntry)
    fun getAllRecipes(): LiveData<List<RecipeEntry>>
    fun deleteRecipe(recipe: RecipeEntry)
    fun updateRecipe(recipe: RecipeEntry)
    fun getRecipeWithId(key: String): LiveData<RecipeEntry>
    fun getFavorites(): LiveData<List<RecipeEntry>>

}