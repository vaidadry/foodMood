package vaida.dryzaite.foodmood.ui.recipeList

import android.content.res.Resources
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.repository.RecipeRepository
import vaida.dryzaite.foodmood.utilities.convertStringMealTypeToNumeric

class RecipeListViewModel @ViewModelInject constructor(private val repository: RecipeRepository) : ViewModel() {

    private lateinit var _recipe: RecipeEntry

    // navigation state
    private val _navigateToRecipeDetail = MutableLiveData<RecipeEntry?>()
    val navigateToRecipeDetail : LiveData<RecipeEntry?> = _navigateToRecipeDetail

    private val _mealSelection = MutableLiveData<Int?>()
    val mealSelection: LiveData<Int?> = _mealSelection

    // action of FAB click
    private val _navigateToAddRecipeFragment = MutableLiveData<Boolean?>()
    val navigateToAddRecipeFragment: LiveData<Boolean?> = _navigateToAddRecipeFragment

    // favorite button state
    private val _favoriteStatusChange = MutableLiveData<Boolean?>()
    val favoriteStatusChange: LiveData<Boolean?> = _favoriteStatusChange

    init {
        _mealSelection.value = null
    }

    private fun getAllRecipesLiveData() = repository.getAllRecipes()
    val allRecipes = repository.getAllRecipes()


    private fun deleteRecipe(recipeEntry: RecipeEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteRecipe(recipeEntry)
    }

    // favorites update
    private fun updateRecipe(recipeEntry: RecipeEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateRecipe(recipeEntry)
    }

    fun onDeleteRecipe(recipeEntry: RecipeEntry) = deleteRecipe(recipeEntry)

    fun onRecipeClicked(recipeEntry: RecipeEntry) {
        _navigateToRecipeDetail.value = recipeEntry
    }

    fun onRecipeDetailNavigated() {
        _navigateToRecipeDetail.value = null
    }

    fun onFabClick() {
        _navigateToAddRecipeFragment.value = true
    }

    fun onFabClicked() {
        _navigateToAddRecipeFragment.value = null
    }

    // favorite button clicks
    fun removeFavorites(recipeEntry: RecipeEntry) {
        _recipe = recipeEntry
        _recipe.isFavorite = false
        updateRecipe(_recipe)
        _favoriteStatusChange.value = true
    }

    fun addFavorites(recipeEntry: RecipeEntry) {
        _recipe = recipeEntry
        _recipe.isFavorite = true
        updateRecipe(_recipe)
        _favoriteStatusChange.value = true
    }

    fun onFavoriteClickCompleted() {
        _favoriteStatusChange.value = null
    }

    fun initFilter(): LiveData<List<RecipeEntry>> =
        Transformations.switchMap(_mealSelection) {meal ->
            if (meal != null) {
                repository.getFilteredRecipes(meal)
            } else {
            getAllRecipesLiveData()}
        }

    // filtering
    fun onMealSelected(titleOrNull: String, resources: Resources){
        _mealSelection.value =
            when (titleOrNull) {
                "null" -> null
                else -> convertStringMealTypeToNumeric(titleOrNull, resources)
            }
    }
}




