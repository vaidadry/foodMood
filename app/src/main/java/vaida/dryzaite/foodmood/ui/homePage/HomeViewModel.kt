package vaida.dryzaite.foodmood.ui.homePage

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.*
import timber.log.Timber
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.data.RecipeRepository

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val repository: RecipeRepository = Injection.provideRecipeRepository(application)


    // "_" means that it is a backing property; in fragment only original ones must be used.
    //property to hold meal selection
    private val _meal = MutableLiveData<Int?>()
    val meal : LiveData<Int?>
        get() = _meal

    var veggie = ObservableField<Boolean>(false)
    var fish = ObservableField<Boolean>(false)


    //property to hold filtered items and by checkbox selections filtered Items
    val filteredRecipes: LiveData<List<RecipeEntry>>
    private var filteredRecipes2: List<RecipeEntry>? = ArrayList()


    //property to hold random item
    private val _randomRecipe = MutableLiveData<RecipeEntry>()
    val randomRecipe: LiveData<RecipeEntry>
        get() = _randomRecipe


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

    //handing meal radio selection and checkbox clicks:
    fun onSetMealType(mealSelection: Int) {
        _meal.value = mealSelection
    }

    // selecting random recipe based on checkbox selections
    private fun getRandomRecipe(): RecipeEntry? {
        Timber.i("veggie selection : ${veggie.get()}, fish selection: ${fish.get()}, meal selection: ${_meal.value}")
        Timber.i("filtered recipes- data set: ${filteredRecipes.value?.size} recipes")

       filterByCheckboxSelections()

        //picking random item, if none available - return null
        if (!filteredRecipes2.isNullOrEmpty()) {
            _randomRecipe.value = filteredRecipes2!!.random()
        } else {
            _randomRecipe.value = null
        }
        Timber.i("randomly generated entry: ${_randomRecipe.value}")
        return _randomRecipe.value
    }

    // checkbox filtering: if nothing selected, means that items under selections will be excluded !!
    // (not included as ALL)
    private fun filterByCheckboxSelections() {
        if (!filteredRecipes.value.isNullOrEmpty()) {
            filteredRecipes2 = filteredRecipes.value?.filter { it.fish == fish.get() && it.veggie == veggie.get() }
        }
        Timber.i("filtered recipes by checkboxes: ${filteredRecipes2?.size} recipes")
    }


    // handling navigation
    private val _navigateToSuggestionPage = MutableLiveData<Boolean?>()
    val navigateToSuggestionPage: LiveData<Boolean?>
        get() = _navigateToSuggestionPage

    fun onGenerateButtonClick() {
        _navigateToSuggestionPage.value = getRandomRecipe() != null
    }
    fun doneNavigating() {
        _navigateToSuggestionPage.value = null
    }

}
