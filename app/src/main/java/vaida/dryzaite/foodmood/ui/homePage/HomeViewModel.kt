package vaida.dryzaite.foodmood.ui.homePage

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.repository.RecipeRepository

class HomeViewModel @ViewModelInject constructor(private val repository: RecipeRepository) : ViewModel() {

    private val _meal = MutableLiveData<Int >()

    var veggie = ObservableField(false)
    var fish = ObservableField(false)

    // property to hold filtered items and by checkbox selections filtered Items
    val filteredRecipes: LiveData<List<RecipeEntry>>
    private var filteredRecipes2: List<RecipeEntry>? = ArrayList()

    private val _randomRecipe = MutableLiveData<RecipeEntry?>()
    val randomRecipe: LiveData<RecipeEntry?> = _randomRecipe

    // if no radiobutton selected - all data is used, if meal selected - only from that data set
    init {
        _meal.value = 0
        filteredRecipes = Transformations.switchMap(_meal) { selection ->
            if (selection != 0) {
                return@switchMap repository.getFilteredRecipes(selection!!)
            } else {
                return@switchMap repository.getAllRecipes()
            }
        }
    }

    // handing meal radio selection
    fun onSetMealType(mealSelection: Int = 0) {
        _meal.value = mealSelection
    }

    private fun getRandomRecipe(): RecipeEntry? {
        filterByCheckboxSelections()

        if (!filteredRecipes2.isNullOrEmpty()) {
            _randomRecipe.value = filteredRecipes2!!.random()
        } else {
            _randomRecipe.value = null
        }
        return _randomRecipe.value
    }

    // checkbox filtering: if nothing selected, means that items under selections will be excluded !!
    // (not included as ALL)
    private fun filterByCheckboxSelections() {
        filteredRecipes2 = if (!filteredRecipes.value.isNullOrEmpty()) {
            filteredRecipes.value?.filter { it.fish == fish.get() && it.veggie == veggie.get() }
        } else {
            null
        }
    }

    private val _navigateToSuggestionPage = MutableLiveData<Boolean?>()
    val navigateToSuggestionPage: LiveData<Boolean?>
        get() = _navigateToSuggestionPage

    fun onGenerateButtonClick() {
        _navigateToSuggestionPage.value = getRandomRecipe() != null
    }
    fun doneNavigating() {
        _navigateToSuggestionPage.value = null
        _randomRecipe.value = null
    }
}
