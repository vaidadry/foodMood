package vaida.dryzaite.foodmood.ui.recipePage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.room.RecipeRepository

class RecipeViewModel (keyId: String, application: Application): AndroidViewModel(application) {

    private val repository: RecipeRepository = Injection.provideRecipeRepository(application)

    private val _detailRecipe: LiveData<RecipeEntry>

    init {
        _detailRecipe = repository.getRecipeWithId(keyId)
    }

    fun getDetailRecipe() = _detailRecipe

    //for use of UI
    val recipeDetail = getDetailRecipe()

    //updating database with status of favorites
    fun updateRecipe(recipe: RecipeEntry) {
        repository.updateRecipe(recipe)
    }


    // manage clicks on url link
    private val _navigateToUrl = MutableLiveData<String?>()
    val navigateToUrl: LiveData<String?>
        get() = _navigateToUrl

    fun onClickUrl() {
        _navigateToUrl.value = recipeDetail.value?.recipe
    }

    fun onButtonClicked() {
        _navigateToUrl.value = null
    }
}