package vaida.dryzaite.foodmood.ui.recipeList


import android.app.Application
import androidx.lifecycle.*
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.room.RecipeRepository


// ViewModel for recipeList fragment interacts with data from the repository
class RecipeListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository =  Injection.provideRecipeRepository(application)

    fun getAllRecipesLiveData() = repository.getAllRecipes()

    fun onDeleteRecipe(recipe: RecipeEntry) = repository.deleteRecipe(recipe)


    //defining navigation state
    private val _navigateToRecipeDetail = MutableLiveData<String>()
    val navigateToRecipeDetail
        get() = _navigateToRecipeDetail


    fun onRecipeClicked(id:String) {
        _navigateToRecipeDetail.value = id
    }

    ///and method to stop navigating
    fun onRecipeDetailNavigated() {
        _navigateToRecipeDetail.value = null
    }
}