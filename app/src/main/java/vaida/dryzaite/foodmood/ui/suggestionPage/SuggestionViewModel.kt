package vaida.dryzaite.foodmood.ui.suggestionPage

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vaida.dryzaite.foodmood.model.RecipeEntry
import javax.inject.Inject

class SuggestionViewModel @Inject constructor(): ViewModel() {

    private val _randomRecipe = MutableLiveData<RecipeEntry?>()
    val randomRecipe: LiveData<RecipeEntry?>
        get() = _randomRecipe

    private val _navigateToUrl = MutableLiveData<String?>()
    val navigateToUrl: LiveData<String?>
    get() = _navigateToUrl


    fun onButtonClick(url: String?) {
        _navigateToUrl.value = url
    }

    fun onButtonClicked() {
        _navigateToUrl.value = null
    }

    //grab passed arguments from Home Fragment to VM
    fun setRecipe(recipe: RecipeEntry?) {
        if (_randomRecipe.value != recipe) {
            _randomRecipe.value = recipe
        }
    }

}