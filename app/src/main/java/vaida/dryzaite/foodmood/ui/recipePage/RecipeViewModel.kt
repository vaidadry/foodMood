package vaida.dryzaite.foodmood.ui.recipePage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.repository.RecipeRepository

class RecipeViewModel @ViewModelInject constructor(private val repository: RecipeRepository): ViewModel() {

    private val _recipe = MutableLiveData<RecipeEntry?>()
    val recipe: LiveData<RecipeEntry?> = _recipe

    private val _navigateToUrl = MutableLiveData<String?>()
    val navigateToUrl: LiveData<String?> = _navigateToUrl

    // args from Fragment
    fun setRecipe(recipe: RecipeEntry?) {
        recipe?.let {
            if (_recipe.value != recipe) {
                _recipe.value = recipe
            }
        }
    }

    // favorites update
    fun updateRecipe(recipeEntry: RecipeEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateRecipe(recipeEntry)
    }

    fun onClickUrl() {
        _navigateToUrl.value = _recipe.value?.recipe
    }

    fun onButtonClicked() {
        _navigateToUrl.value = null
    }
}