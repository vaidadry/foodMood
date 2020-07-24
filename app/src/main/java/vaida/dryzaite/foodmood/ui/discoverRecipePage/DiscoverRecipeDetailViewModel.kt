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


    //manage clicks on AddButton
    private val _navigateToAddFragment = MutableLiveData<ExternalRecipe?>()
    val navigateToAddRecipe: LiveData<ExternalRecipe?>
        get() = _navigateToAddFragment

    fun onClickAddFragment(){
        _navigateToAddFragment.value = _selectedRecipe.value
    }

    fun onClickAddFragmentComplete(){
        _navigateToAddFragment.value = null
    }

}
