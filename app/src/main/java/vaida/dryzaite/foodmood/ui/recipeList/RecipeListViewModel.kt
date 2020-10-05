package vaida.dryzaite.foodmood.ui.recipeList

import android.content.res.Resources
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.repository.RecipeRepository
import vaida.dryzaite.foodmood.utilities.convertStringMealTypeToNumeric
import javax.inject.Inject

class RecipeListViewModel @Inject constructor(private val repository: RecipeRepository) : ViewModel() {

    //defining navigation state
    private val _navigateToRecipeDetail = MutableLiveData<RecipeEntry>()
    val navigateToRecipeDetail : LiveData<RecipeEntry>
        get() = _navigateToRecipeDetail


    // storing meal selection filter value
    private val _mealSelection = MutableLiveData<Int?>()
    val mealSelection: LiveData<Int?>
        get() = _mealSelection


    //storing action of clicking FAB
    private val _navigateToAddRecipeFragment = MutableLiveData<Boolean?>()
    val navigateToAddRecipeFragment: LiveData<Boolean?>
        get() = _navigateToAddRecipeFragment


    // storing favorite button state
    private val _favoriteStatusChange = MutableLiveData<Boolean?>()
    val favoriteStatusChange: LiveData<Boolean?>
        get() = _favoriteStatusChange


    private lateinit var _recipe: RecipeEntry


    init {
        _mealSelection.value = null
    }

    private fun getAllRecipesLiveData() = repository.getAllRecipes()
    val allRecipes = repository.getAllRecipes()


    private fun deleteRecipe(recipeEntry: RecipeEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteRecipe(recipeEntry)
    }

    //updating database with changed status of favorites
    private fun updateRecipe(recipeEntry: RecipeEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateRecipe(recipeEntry)
    }


    fun onDeleteRecipe(recipeEntry: RecipeEntry) = deleteRecipe(recipeEntry)


    // handling navigation
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


    //handling favorite button clicks
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


    // handling filtering
    fun onMealSelected(titleOrNull: String, resources: Resources){
        _mealSelection.value =
            when (titleOrNull) {
                "null" -> null
                else -> convertStringMealTypeToNumeric(titleOrNull, resources)
            }
    }


    fun initFilter(): LiveData<List<RecipeEntry>> =
        Transformations.switchMap(_mealSelection) {meal ->
            if (meal != null) {
                repository.getFilteredRecipes(meal)
            } else {
            getAllRecipesLiveData()}
        }

}




