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

    //to store TITLE search RESULT: recipeList
    private var currentTitleSearchResult: Flow<PagingData<ExternalRecipe>>? = null

    //to store title search QUERY
    private val _currentTitleSearchQuery = MutableLiveData<String>("")
    val currentTitleSearchQuery: LiveData<String> = _currentTitleSearchQuery

    fun updateTitleSearchQuery(searchQuery: String) {
        _currentTitleSearchQuery.value = searchQuery
    }

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
        Timber.i("current result: ${currentTitleSearchResult} new search result: $newResult")
        return newResult

    }

}
