package vaida.dryzaite.foodmood.ui.favoritesPage

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.repository.RecipeRepository
import javax.inject.Inject

class FavoritesViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {

    fun getFavorites() = repository.getFavorites()

    //updating database with changed status of favorites
    private fun updateRecipe(recipeEntry: RecipeEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateRecipe(recipeEntry)
    }

    //defining navigation state
    private val _navigateToRecipeDetail = MutableLiveData<RecipeEntry?>()
    val navigateToRecipeDetail
        get() = _navigateToRecipeDetail


    fun onRecipeClicked(recipe: RecipeEntry) {
        _navigateToRecipeDetail.value = recipe
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