package vaida.dryzaite.foodmood.ui.discoverRecipes

import android.app.Application
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import timber.log.Timber
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.roomRecipeBook.RecipeRepository
import vaida.dryzaite.foodmood.network.ExternalRecipe
import vaida.dryzaite.foodmood.network.RecipeApi
import java.io.IOException
import java.lang.Exception

//defining states of web request
enum class RecipeApiStatus { LOADING, ERROR, DONE }

class DiscoverRecipesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository = Injection.provideRecipeRepository(application)

    // The internal MutableLiveData and external immutable LiveData that stores the most recent response
    private val _status = MutableLiveData<RecipeApiStatus>()
    val status: LiveData<RecipeApiStatus>
        get() = _status


    // The internal MutableLiveData and external immutable LiveData that stores list data
//    private val _externalRecipes = MutableLiveData<List<ExternalRecipe>>()
//    val externalRecipes: LiveData<List<ExternalRecipe>>
//        get() = _externalRecipes

    var externalRecipes = repository.results


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
//    private val _searchQuery =  MutableLiveData<String?>()
    var searchQueryVM =  MutableLiveData<String?>()
//        get() = _searchQuery

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(
        viewModelJob + Dispatchers.Main)


    init {
        getExternalRecipes()
    }

    //this creates and starts network call on a bckgrnd thread returning deferred object
//    status defines stages network call is in and its actions
    private fun getExternalRecipes() {
        _status.value = RecipeApiStatus.LOADING
       viewModelScope.launch {
           try {
               repository.refreshExternalRecipes()
               _status.value = RecipeApiStatus.DONE

           } catch (networkError: IOException){
                   if(externalRecipes.value.isNullOrEmpty()) {
                       _status.value = RecipeApiStatus.ERROR
                   }
           }
       }
    }

    private fun searchExternalRecipes(searchQuery: String?) {
        Timber.i("launching coroutines with search query $searchQuery")
        _status.value = RecipeApiStatus.LOADING
        viewModelScope.launch {
            try {
                repository.searchExternalRecipes(searchQuery)
                _status.value = RecipeApiStatus.DONE

            } catch (networkError: IOException){
                if(externalRecipes.value.isNullOrEmpty()) {
                    _status.value = RecipeApiStatus.ERROR
                }
            }
        }
    }


    fun getExternalFilterResults(searchQuery: String?) {
        Timber.i("getting filter results: $searchQuery")
        searchExternalRecipes(searchQuery)

    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
