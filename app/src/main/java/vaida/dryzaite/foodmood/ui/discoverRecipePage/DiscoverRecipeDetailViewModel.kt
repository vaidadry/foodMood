package vaida.dryzaite.foodmood.ui.discoverRecipePage

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vaida.dryzaite.foodmood.network.ExternalRecipe
import javax.inject.Inject

class DiscoverRecipeDetailViewModel @ViewModelInject constructor()
    : ViewModel() {

    //val to hold data on clicked item
    private val _selectedRecipe = MutableLiveData<ExternalRecipe?>()
    val selectedRecipe: LiveData<ExternalRecipe?>
        get() = _selectedRecipe

    //grab passed arguments from Fragment to VM
    fun setRecipe(recipe: ExternalRecipe?) {
        if (_selectedRecipe.value != recipe) {
            _selectedRecipe.value = recipe
        }
    }

        // manage clicks on url link
    private val _navigateToUrl = MutableLiveData<String?>()
    val navigateToUrl: LiveData<String?>
        get() = _navigateToUrl

    fun onClickUrl() {
        _navigateToUrl.value = _selectedRecipe.value?.href
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
