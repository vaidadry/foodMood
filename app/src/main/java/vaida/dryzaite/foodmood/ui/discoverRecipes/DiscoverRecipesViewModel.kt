package vaida.dryzaite.foodmood.ui.discoverRecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import vaida.dryzaite.foodmood.network.ExternalRecipe
import vaida.dryzaite.foodmood.network.RecipeApi
import java.lang.Exception

class DiscoverRecipesViewModel : ViewModel() {

    // The internal MutableLiveData and external immutable LiveData that stores the most recent response
    private val _response = MutableLiveData<String>()
    val response: LiveData<String>
        get() = _response

    // The internal MutableLiveData and external immutable LiveData that stores single recipe data
    private val _externalRecipes = MutableLiveData<List<ExternalRecipe>>()
    val externalRecipes: LiveData<List<ExternalRecipe>>
        get() = _externalRecipes

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main)

    init {
        getExternalRecipes()
    }

    //this creates and starts network call on a bckgrnd thread returning deferred object
    private fun getExternalRecipes() {
       coroutineScope.launch {
           try {
               val getExternalRecipesDeferred = RecipeApi.retrofitService.getRecipesAsync()
               val listResult = getExternalRecipesDeferred.await().results
               _externalRecipes.value = listResult
           } catch (e: Exception){
               _response.value = "Failure: ${e.message}"
           }
       }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
