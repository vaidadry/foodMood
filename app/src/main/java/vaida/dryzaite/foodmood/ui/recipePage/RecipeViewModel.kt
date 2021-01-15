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

    private val _navigateToAddFragment = MutableLiveData<RecipeEntry?>()
    val navigateToAddRecipe: LiveData<RecipeEntry?> = _navigateToAddFragment

    private val _notInDatabase = MutableLiveData<Boolean?>()
    val notInDatabase: LiveData<Boolean?> = _notInDatabase

    // args from Fragment
    fun setRecipe(recipe: RecipeEntry?) {
        checkDatabase(recipe?.id)
        recipe?.let {
            if (_recipe.value != it) {
                _recipe.value = it
            }
        }
    }

    // favorite status update
    fun updateRecipe(recipeEntry: RecipeEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateRecipe(recipeEntry)
    }

    fun onClickUrl() {
        _navigateToUrl.value = _recipe.value?.href
    }

    fun onButtonClicked() {
        _navigateToUrl.value = null
    }

    fun onClickAddFragment(){
        _navigateToAddFragment.value = _recipe.value
    }

    fun onClickAddFragmentComplete(){
        _navigateToAddFragment.value = null
    }

    private fun checkDatabase(id: String?) {
        if (id == null) {
            _notInDatabase.postValue(true)
        } else {
            val example = repository.getAllRecipes().value
            _notInDatabase.postValue(example?.filter { it.id == id}?.size == 0)
        }
    }
}