package vaida.dryzaite.foodmood.ui.discoverRecipes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import vaida.dryzaite.foodmood.network.ExternalRecipe
import vaida.dryzaite.foodmood.repository.RecipeRepository

@ExperimentalCoroutinesApi
class DiscoverRecipesIngredientViewModel @ViewModelInject constructor (
    private val repository: RecipeRepository
) : ViewModel() {

    private var currentIngredientListQueryValue: List<String>? = null
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

    private val _navigateToSelectedRecipe = MutableLiveData<ExternalRecipe?>()
    val navigateToSelectedRecipe: LiveData<ExternalRecipe?> = _navigateToSelectedRecipe


    fun displayRecipeDetails(externalRecipe: ExternalRecipe) {
        _navigateToSelectedRecipe.value = externalRecipe
    }

    fun displayRecipeDetailsComplete() {
        _navigateToSelectedRecipe.value = null
    }


    // method checks if query old or new, if old - uses cache, if new - makes network call
    fun searchExternalRecipesByIngredient(queryList: List<String>) : Flow<PagingData<ExternalRecipe>> {
        Timber.i("launching search of queryList: $queryList")
//        val lastResult = currentIngredientSearchResult
//        Timber.i("lastResult: $lastResult")

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