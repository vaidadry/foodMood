package vaida.dryzaite.foodmood.ui.recipeList


import android.app.Application
import androidx.lifecycle.*
import timber.log.Timber
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.room.RecipeRepository

// ViewModel for recipeList fragment interacts with data from the repository
class RecipeListViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository = Injection.provideRecipeRepository(application)

    fun getAllRecipesLiveData() = repository.getAllRecipes()

    fun onDeleteRecipe(recipe: RecipeEntry) = repository.deleteRecipe(recipe)

    //updating database with changed status of favorites
    fun updateRecipe(recipe: RecipeEntry) = repository.updateRecipe(recipe)

    fun getRecipeById(id: String) = repository.getRecipeWithId(id)

    //defining navigation state
    private val _navigateToRecipeDetail = MutableLiveData<String>()
    val navigateToRecipeDetail
        get() = _navigateToRecipeDetail


    fun onRecipeClicked(id: String) {
        _navigateToRecipeDetail.value = id
    }

    ///and method to stop navigating
    fun onRecipeDetailNavigated() {
        _navigateToRecipeDetail.value = null
    }




//neveikia apacioj!

    private val _favoriteStatusChange = MutableLiveData<Boolean?>()
    val favoriteStatusChange: LiveData<Boolean?>
        get() = _favoriteStatusChange


    fun onFavoriteClicked(recipe: RecipeEntry) {
        val recipeData = getRecipeById(recipe.id)
        Timber.i("live data  get by id ${recipeData.value}")
        when (val favStatus = recipeData.value!!.isFavorite) {
            true -> !favStatus
            false -> favStatus
        }
        Timber.i("live data  after WHEN  ${recipeData.value}")
        updateRecipe(recipeData.value!!)
        Timber.i("live data  after update  ${recipeData.value}")
        _favoriteStatusChange.value = true
    }

    fun onFavoriteClickCompleted() {
        _favoriteStatusChange.value = null
    }
}