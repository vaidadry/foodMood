package vaida.dryzaite.foodmood.model

import androidx.lifecycle.LiveData


//REPO is mediator between database and viewmodel
interface RecipeRepository {
    fun saveNewRecipe(recipeEntry: RecipeEntry)
    fun getAllRecipes(): LiveData<List<RecipeEntry>>
    fun deleteRecipe(recipeEntry: RecipeEntry)
    fun updateRecipe(recipeEntry: RecipeEntry)
}