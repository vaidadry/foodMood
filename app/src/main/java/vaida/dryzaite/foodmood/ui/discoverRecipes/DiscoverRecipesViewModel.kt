package vaida.dryzaite.foodmood.ui.discoverRecipes

import android.app.Application
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.data.RecipeRepository
import vaida.dryzaite.foodmood.network.ExternalRecipe

//defining states of web request
enum class RecipeApiStatus { LOADING, ERROR, DONE }

@ExperimentalCoroutinesApi
class DiscoverRecipesViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: RecipeRepository = Injection.provideRecipeRepository(application)

    // stores the most recent responses of status, query, searchresult
    private val _status = MutableLiveData<RecipeApiStatus>()
    val status: LiveData<RecipeApiStatus>
        get() = _status

    private var currentQueryValue: String? = null
    private var currentSearchResult: Flow<PagingData<ExternalRecipe>>? = null


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


    // method checks if query old or new, if old - uses cache, if new - makes network call
    fun searchExternalRecipes(searchQuery: String) : Flow<PagingData<ExternalRecipe>> {
        Timber.i("launching searchQuery: $searchQuery")
        val lastResult = currentSearchResult
        Timber.i("lastResult: ${lastResult}")
        if (searchQuery == currentQueryValue && lastResult != null) {
            Timber.i("old search result: ${lastResult}")
            return lastResult
        }
        currentQueryValue = searchQuery
        Timber.i("current query: $currentQueryValue")

        val newResult: Flow<PagingData<ExternalRecipe>> =
            repository.searchExternalRecipes(searchQuery)
                .cachedIn(viewModelScope)
        currentSearchResult = newResult
        Timber.i("new search result: $newResult")
        return newResult
    }

    // for states!

//        viewModelScope.launch {
//            try {
//                _status.value = RecipeApiStatus.LOADING
//                repository.searchExternalRecipes(searchQuery)
//                _status.value = RecipeApiStatus.DONE
//
//            } catch (networkError: NetworkErrorException){
//                if(externalRecipes.value.isNullOrEmpty()) {
//                    _status.value = RecipeApiStatus.ERROR
//                }
//            }
//        }


}
