package vaida.dryzaite.foodmood.ui.favoritesPage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.repository.RecipeRepository

class FavoritesViewModel @ViewModelInject constructor(
    private val repository: RecipeRepository
) : ViewModel() {

    private lateinit var _recipe: RecipeEntry

    // navigation state
    private val _navigateToRecipeDetail = MutableLiveData<RecipeEntry?>()
    val navigateToRecipeDetail = _navigateToRecipeDetail

    private val _favoriteStatusChange = MutableLiveData<Boolean?>()
    val favoriteStatusChange: LiveData<Boolean?> = _favoriteStatusChange

    fun getFavorites() = repository.getFavorites()

    // updates database with changed favorites
    private fun updateRecipe(recipeEntry: RecipeEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateRecipe(recipeEntry)
    }

    fun onRecipeClicked(recipe: RecipeEntry) {
        _navigateToRecipeDetail.value = recipe
    }

    // stops navigating
    fun onRecipeDetailNavigated() {
        _navigateToRecipeDetail.value = null
    }

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