package vaida.dryzaite.foodmood.ui.discoverRecipes

import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import vaida.dryzaite.foodmood.repository.RecipeRepository
import vaida.dryzaite.foodmood.network.ExternalRecipe
import javax.inject.Inject


@ExperimentalCoroutinesApi
class DiscoverRecipesViewModel @Inject constructor (
    private val repository: RecipeRepository) : ViewModel() {

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
        Timber.i("lastResult: $lastResult")
        if (searchQuery == currentQueryValue && lastResult != null) {
            Timber.i("old search result: $lastResult")
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

}
