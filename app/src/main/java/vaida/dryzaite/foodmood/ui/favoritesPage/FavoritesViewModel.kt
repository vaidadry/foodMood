package vaida.dryzaite.foodmood.ui.favoritesPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.data.RecipeRepository

class FavoritesViewModel(application: Application) : AndroidViewModel(application){

    private val repository: RecipeRepository = Injection.provideRecipeRepository(application)

    fun getFavorites() = repository.getFavorites()

    //updating database with changed status of favorites
    private fun updateRecipe(recipeEntry: RecipeEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateRecipe(recipeEntry)
    }

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

    //handling Favorite data updates in DB

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