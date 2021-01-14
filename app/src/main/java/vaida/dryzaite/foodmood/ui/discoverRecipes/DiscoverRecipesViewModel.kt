package vaida.dryzaite.foodmood.ui.discoverRecipes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import vaida.dryzaite.foodmood.repository.RecipeRepository
import vaida.dryzaite.foodmood.network.ExternalRecipe

@ExperimentalCoroutinesApi
class DiscoverRecipesViewModel @ViewModelInject constructor (
    private val repository: RecipeRepository) : ViewModel() {

    private var currentTitleSearchResult: Flow<PagingData<ExternalRecipe>>? = null

    private val _currentTitleSearchQuery = MutableLiveData("")
    val currentTitleSearchQuery: LiveData<String> = _currentTitleSearchQuery

    fun updateTitleSearchQuery(searchQuery: String) {
        _currentTitleSearchQuery.value = searchQuery
    }

    private val _navigateToSelectedRecipe = MutableLiveData<ExternalRecipe?>()
    val navigateToSelectedRecipe: LiveData<ExternalRecipe?> = _navigateToSelectedRecipe

    fun displayRecipeDetails(externalRecipe: ExternalRecipe) {
        _navigateToSelectedRecipe.value = externalRecipe
    }

    fun displayRecipeDetailsComplete() {
        _navigateToSelectedRecipe.value = null
    }

    // method checks if query old or new, if old - uses cache, if new - makes network call
    //p.s. using old query does not refresh data somehow, can use cache
    fun searchExternalRecipesByTitle(searchQuery: String) : Flow<PagingData<ExternalRecipe>> {
        Timber.i("launching searchExternalRecipesByTitle() searchQuery: $searchQuery")

//        val previousResult = currentTitleSearchResult
//        Timber.i("previousResult: $previousResult")
//
//        if (searchQuery == _currentTitleSearchQuery.value && previousResult != null) {
//            Timber.i("old search result: ${previousResult}")
//            return previousResult
//        }
//        updateTitleSearchQuery(searchQuery)
// updating this causes the loop, if no old query is used

        Timber.i("current query: ${currentTitleSearchQuery.value}, searchQuery = $searchQuery")

        val newResult: Flow<PagingData<ExternalRecipe>> =
            repository.searchExternalRecipes(searchQuery)
                .cachedIn(viewModelScope)
        currentTitleSearchResult = newResult
        Timber.i("current result: $currentTitleSearchResult new search result: $newResult")
        return newResult

    }
}
