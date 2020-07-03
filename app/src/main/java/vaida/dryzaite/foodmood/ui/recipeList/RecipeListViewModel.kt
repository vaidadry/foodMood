package vaida.dryzaite.foodmood.ui.recipeList


import android.app.Application
import androidx.lifecycle.*
//import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.RecipeRepository
import vaida.dryzaite.foodmood.model.room.RecipeDao


// ViewModel for recipeList fragment interacts with data from the repository
class RecipeListViewModel(dataSource:RecipeDao) : ViewModel() {

//    private val repository: RecipeRepository =  Injection.provideRecipeRepository()
//
//    private val allRecipesLiveData = repository.getAllRecipes()
//
//    fun getAllRecipesLiveData() = allRecipesLiveData
//
//    fun deleteRecipe(recipe: RecipeEntry) = repository.deleteRecipe(recipe)

    val database = dataSource
    private val allRecipesLiveData = database.getAllRecipes()
    fun getAllRecipesLiveData() = allRecipesLiveData

    fun deleteRecipe(recipe: RecipeEntry) = database.deleteRecipe(recipe)






}