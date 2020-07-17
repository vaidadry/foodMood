package vaida.dryzaite.foodmood.ui.recipeList


import android.app.Application
import androidx.lifecycle.*
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.room.RecipeRepository

// ViewModel for recipeList fragment interacts with data from the repository
class RecipeListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository = Injection.provideRecipeRepository(application)

    fun getAllRecipesLiveData() = repository.getAllRecipes()

    fun onDeleteRecipe(recipe: RecipeEntry) = repository.deleteRecipe(recipe)

    //updating database with changed status of favorites
    private fun updateRecipe(recipe: RecipeEntry) = repository.updateRecipe(recipe)

    fun getRecipeById(id: String) = repository.getRecipeWithId(id)

    //defining navigation state
    private val _navigateToRecipeDetail = MutableLiveData<String>()
    val navigateToRecipeDetail
        get() = _navigateToRecipeDetail


    fun onRecipeClicked(id: String) {
        _navigateToRecipeDetail.value = id
    }

    ///and method to stop navigating
    fun onRecipeDetailNavigated() {
        _navigateToRecipeDetail.value = null
    }


    //defining action of FAB
    private val _navigateToAddRecipeFragment = MutableLiveData<Boolean?>()
    val navigateToAddRecipeFragment: LiveData<Boolean?>
        get() = _navigateToAddRecipeFragment


    fun onFabClick() {
        _navigateToAddRecipeFragment.value = true
    }

    fun onFabClicked() {
        _navigateToAddRecipeFragment.value = null
    }



    //defining favorite button state
    private val _favoriteStatusChange = MutableLiveData<Boolean?>()
    val favoriteStatusChange: LiveData<Boolean?>
        get() = _favoriteStatusChange


    private lateinit var _recipe: RecipeEntry

    fun removeFavorites(recipe: RecipeEntry) {
        _recipe = recipe
        _recipe.isFavorite = false
        updateRecipe(_recipe)
        _favoriteStatusChange.value = true
    }

    fun addFavorites(recipe: RecipeEntry) {
        _recipe = recipe
        _recipe.isFavorite = true
        updateRecipe(_recipe)
        _favoriteStatusChange.value = true
    }

    fun onFavoriteClickCompleted() {
        _favoriteStatusChange.value = null
    }
}