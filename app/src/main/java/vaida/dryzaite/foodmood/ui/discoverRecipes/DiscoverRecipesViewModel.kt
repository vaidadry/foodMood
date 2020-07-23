package vaida.dryzaite.foodmood.ui.discoverRecipes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import vaida.dryzaite.foodmood.network.ExternalRecipe
import vaida.dryzaite.foodmood.network.RecipeApi
import java.lang.Exception

//defining states of web request
enum class RecipeApiStatus { LOADING, ERROR, DONE }

class DiscoverRecipesViewModel : ViewModel() {

    // The internal MutableLiveData and external immutable LiveData that stores the most recent response
    private val _status = MutableLiveData<RecipeApiStatus>()
    val status: LiveData<RecipeApiStatus>
        get() = _status


    // The internal MutableLiveData and external immutable LiveData that stores single recipe data
    private val _externalRecipes = MutableLiveData<List<ExternalRecipe>>()
    val externalRecipes: LiveData<List<ExternalRecipe>>
        get() = _externalRecipes


    //val to trigger navigation to detail page and related methods
    private val _navigateToSelectedRecipe = MutableLiveData<ExternalRecipe>()
    val navigateToSelectedRecipe: LiveData<ExternalRecipe>
        get() = _navigateToSelectedRecipe

    fun displayRecipeDetails(externalRecipe: ExternalRecipe) {
        _navigateToSelectedRecipe.value = externalRecipe
    }

    fun displayRecipeDetailsComplete() {
        _navigateToSelectedRecipe.value = null
    }


    //here user search input gets saved
    private val searchQuery =  MutableLiveData<String?>()

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main)

    init {
        getExternalRecipes(searchQuery.value)
        Timber.i("init value of getingExtRecipes from api ${searchQuery.value}")
    }

    //this creates and starts network call on a bckgrnd thread returning deferred object
//    status defines stages network call is in and its actions
    private fun getExternalRecipes(searchQuery: String?) {
        Timber.i("launching coroutines with search query $searchQuery")
       coroutineScope.launch {
           try {
               val getExternalRecipesDeferred = RecipeApi.retrofitService.getRecipesAsync(searchQuery)
               _status.value = RecipeApiStatus.LOADING
               val listResult = getExternalRecipesDeferred.await().results
               _status.value = RecipeApiStatus.DONE
               _externalRecipes.value = listResult
           } catch (e: Exception){
               _status.value = RecipeApiStatus.ERROR
               _externalRecipes.value = ArrayList()
           }
       }
    }

    fun getFilterResults(searchQuery: String?) {
        Timber.i("geting filter results: $searchQuery")
        getExternalRecipes(searchQuery)
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
