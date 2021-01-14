package vaida.dryzaite.foodmood.ui.suggestionPage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vaida.dryzaite.foodmood.model.RecipeEntry

class SuggestionViewModel @ViewModelInject constructor(): ViewModel() {

    private val _randomRecipe = MutableLiveData<RecipeEntry?>()
    val randomRecipe: LiveData<RecipeEntry?> = _randomRecipe

    private val _navigateToUrl = MutableLiveData<String?>()
    val navigateToUrl: LiveData<String?> = _navigateToUrl
    
    fun setRecipe(recipe: RecipeEntry?) {
        if (_randomRecipe.value != recipe) {
            _randomRecipe.value = recipe
        }
    }
    
    fun onButtonClick(url: String?) {
        _navigateToUrl.value = url
    }

    fun onButtonClicked() {
        _navigateToUrl.value = null
    }
}