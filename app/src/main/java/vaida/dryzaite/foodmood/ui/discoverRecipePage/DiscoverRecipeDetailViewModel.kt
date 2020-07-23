package vaida.dryzaite.foodmood.ui.discoverRecipePage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vaida.dryzaite.foodmood.network.ExternalRecipe

class DiscoverRecipeDetailViewModel (externalRecipe: ExternalRecipe, application: Application): AndroidViewModel(application) {

    //val to hold data on clicked item
    private val _selectedRecipe = MutableLiveData<ExternalRecipe>()
    val selectedRecipe: LiveData<ExternalRecipe>
        get() = _selectedRecipe

    init {
        _selectedRecipe.value = externalRecipe
    }


        // manage clicks on url link
    private val _navigateToUrl = MutableLiveData<String?>()
    val navigateToUrl: LiveData<String?>
        get() = _navigateToUrl

    fun onClickUrl() {
        _navigateToUrl.value = _selectedRecipe.value?.url
    }

    fun onButtonClicked() {
        _navigateToUrl.value = null
    }





}
//    private val repository: RecipeRepository = Injection.provideRecipeRepository(application)
//
//    private val _detailRecipe: LiveData<RecipeEntry>
//
//    init {
//        _detailRecipe = repository.getRecipeWithId(keyId)
//    }
//
//    fun getDetailRecipe() = _detailRecipe
//
//    //for use of UI
//    val recipeDetail = getDetailRecipe()
//
//    //updating database with status of favorites
//    fun updateRecipe(recipe: RecipeEntry) {
//        repository.updateRecipe(recipe)
//    }
//
//

