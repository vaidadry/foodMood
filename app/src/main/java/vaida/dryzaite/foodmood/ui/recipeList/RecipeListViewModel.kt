package vaida.dryzaite.foodmood.ui.recipeList


import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.repository.RecipeRepository
import vaida.dryzaite.foodmood.utilities.convertStringMealTypeToNumeric

// ViewModel for recipeList fragment interacts with data from the repository
class RecipeListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository = Injection.provideRecipeRepository(application)

   private val _recipeList = MutableLiveData<List<RecipeEntry>>()
    val recipeList : LiveData<List<RecipeEntry>>
            get() = _recipeList


    //defining navigation state
    private val _navigateToRecipeDetail = MutableLiveData<String>()
    val navigateToRecipeDetail
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


    private fun deleteRecipe(recipeEntry: RecipeEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.deleteRecipe(recipeEntry)
    }

    //updating database with changed status of favorites
    private fun updateRecipe(recipeEntry: RecipeEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.updateRecipe(recipeEntry)
    }


    fun onDeleteRecipe(recipeEntry: RecipeEntry) = deleteRecipe(recipeEntry)


    // handling navigation
    fun onRecipeClicked(id: String) {
        _navigateToRecipeDetail.value = id
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
    fun onMealSelected(titleOrNull: String){
        _mealSelection.value =
            when (titleOrNull) {
                "null" -> null
                else -> convertStringMealTypeToNumeric(titleOrNull, getApplication<Application>().resources)
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




