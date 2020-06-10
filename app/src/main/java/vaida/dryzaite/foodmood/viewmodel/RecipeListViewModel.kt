package vaida.dryzaite.foodmood.viewmodel

import androidx.lifecycle.ViewModel
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.RecipeRepository


// ViewModel for recipeList fragment interacts with data from the repository
class RecipeListViewModel() : ViewModel() {

    private val repository: RecipeRepository =  Injection.provideRecipeRepository()

    private val allRecipesLiveData = repository.getAllRecipes()

    fun getAllRecipesLiveData() = allRecipesLiveData

    fun deleteRecipe(recipe: RecipeEntry) = repository.deleteRecipe(recipe)

}