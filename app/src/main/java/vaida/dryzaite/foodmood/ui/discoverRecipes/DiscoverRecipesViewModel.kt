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
    private val _currentTitleSearchQuery = MutableLiveData<String>()
    val currentTitleSearchQuery: LiveData<String> = _currentTitleSearchQuery

    fun updateTitleSearchQuery(searchQuery: String) {
        _currentTitleSearchQuery.value = searchQuery
    }

    //to store ingredient search QUERY
    private var currentIngredientListQueryValue: List<String>? = null

    //to store INGREDIENT search RESULT: recipeList
    private var currentIngredientSearchResult: Flow<PagingData<ExternalRecipe>>? = null

    private val _ingredientsListHelper = ArrayList<String>()
    private val _ingredientsList = MutableLiveData<List<String>>(emptyList())
    val ingredientsList: LiveData<List<String>> = _ingredientsList

    fun addIngredientToSearchList(ingredient: String) {
        _ingredientsListHelper.add(ingredient)
        _ingredientsList.value = _ingredientsListHelper
    }
    fun removeIngredientFromSearchList (ingredient: String){
        _ingredientsListHelper.remove(ingredient)
        _ingredientsList.value = _ingredientsListHelper
    }

    fun clearSearchQuery() {
        _ingredientsListHelper.clear()
        _ingredientsList.value = _ingredientsListHelper
        Timber.i(" cleared ingredients list : ${ingredientsList.value}")
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
    fun searchExternalRecipesByTitle(searchQuery: String) : Flow<PagingData<ExternalRecipe>> {
        Timber.i("launching searchQuery: $searchQuery")
//        val previousResult = currentTitleSearchResult
//        Timber.i("previousResult: $previousResult")
//
//        if (searchQuery == _currentTitleSearchQuery.value && previousResult != null) {
//            Timber.i("old search result: $previousResult")
//            return previousResult
//        }
//        updateTitleSearchQuery(searchQuery)

        Timber.i("current query: ${currentTitleSearchQuery.value}, searchQuery = $searchQuery")

        val newResult: Flow<PagingData<ExternalRecipe>> =
            repository.searchExternalRecipes(searchQuery)
                .cachedIn(viewModelScope)
        currentTitleSearchResult = newResult
        Timber.i("new search result: $newResult")
        return newResult
    }

    // method checks if query old or new, if old - uses cache, if new - makes network call
    fun searchExternalRecipesByIngredient(queryList: List<String>) : Flow<PagingData<ExternalRecipe>> {
        Timber.i("launching search of queryList: $queryList")
        val lastResult = currentIngredientSearchResult
        Timber.i("lastResult: $lastResult")

//        if (queryList == currentIngredientListQueryValue && lastResult != null) {
//            Timber.i("old search list: $currentIngredientListQueryValue and query list: $queryList")
//            return lastResult
//        }
        currentIngredientListQueryValue = queryList
        Timber.i("current query: $currentIngredientListQueryValue")

        val newResult: Flow<PagingData<ExternalRecipe>> =
            repository.searchExternalRecipesByIngredient(queryList)
                .cachedIn(viewModelScope)
        currentIngredientSearchResult = newResult
        Timber.i("new search result: $newResult")
        return newResult
    }

}
