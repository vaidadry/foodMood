package vaida.dryzaite.foodmood.ui.recipePage

import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.repository.RecipeRepository
import javax.inject.Inject

class RecipeViewModel @Inject constructor(private val repository: RecipeRepository): ViewModel() {

    private val _recipe = MutableLiveData<RecipeEntry>()
    val recipe: LiveData<RecipeEntry>
        get() = _recipe


    //grab passed arguments from Fragment to VM
    fun setRecipe(recipe: RecipeEntry?) {
        if (_recipe.value != recipe) {
            _recipe.value = recipe
        }
    }


    //updating database with status of favorites
    fun updateRecipe(recipeEntry: RecipeEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateRecipe(recipeEntry)
    }


    // manage clicks on url link
    private val _navigateToUrl = MutableLiveData<String?>()
    val navigateToUrl: LiveData<String?>
        get() = _navigateToUrl


    fun onClickUrl() {
        _navigateToUrl.value = _recipe.value?.recipe
    }


    fun onButtonClicked() {
        _navigateToUrl.value = null
    }
}