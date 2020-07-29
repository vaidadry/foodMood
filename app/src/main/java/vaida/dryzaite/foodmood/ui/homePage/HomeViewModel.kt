package vaida.dryzaite.foodmood.ui.homePage

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.roomRecipeBook.RecipeRepository

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val repository: RecipeRepository = Injection.provideRecipeRepository(application)

    val allRecipesLiveData= repository.getAllRecipes()

//    private val filterRepoRecipes = repository.getFilteredRecipes(meal.value!!) //!! BREAKS THE APP


//    fun getFilteredRecipes(mealSelection: Int) {
////        val demo = repository.getFilteredRecipes(mealSelection)
//        _filteredRecipes.value = filterRepoRecipes.value
//        Timber.i("meal selection: $mealSelection, demo: ${filterRepoRecipes.value}; filteredRec: ${_filteredRecipes.value}")
//    }

    fun getAllRecipes() = allRecipesLiveData


//    private lateinit var filteredList: MutableLiveData<List<RecipeEntry>>




    // "_" means that it is a backing property; in fragment only original ones must be used.

    //property to hold filtered items
    private val _filteredRecipes = MutableLiveData<List<RecipeEntry>>()
    val filteredRecipes: LiveData<List<RecipeEntry>>
    get() = _filteredRecipes

    //property to hold random item
    private val _randomRecipe = MutableLiveData<RecipeEntry>()
    val randomRecipe: LiveData<RecipeEntry>
        get() = _randomRecipe


    private fun getRandomRecipe(): RecipeEntry? {
        Timber.i("veggie selection : ${veggie.get()}, fish selection: ${fish.get()}, meal selection: $_meal")
        val allRecipes = getAllRecipes()
//        Timber.i("all recipes retrieved ${allRecipes.value}")
        if (!allRecipes.value.isNullOrEmpty()) {
            _randomRecipe.value = allRecipes.value?.random()
        } else {
            _randomRecipe.value = null
        }
        Timber.i("randomly generated entry in viewModel: ${_randomRecipe.value}")
        return _randomRecipe.value
    }




//    private fun getRandomRecipe(): RecipeEntry? {
//        Timber.i("veggie selection : ${veggie.get()}, fish selection: ${fish.get()}, meal selection: ${_meal.value}")
//        if (!_filteredRecipes.value.isNullOrEmpty()) {
//            _randomRecipe.value = _filteredRecipes.value?.random()
//        } else {
//            _randomRecipe.value = null
//        }
//        Timber.i("randomly generated entry in viewModel: ${_randomRecipe.value}")
//        return _randomRecipe.value




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


    //handing meal radio selection and checkbox clicks:

    fun onSetMealType(mealSelection: Int) {
        _meal.value = mealSelection
//        meal.set(mealSelection)
        Timber.i("meal selected ${_meal.value}")
    }

    private val _meal = MutableLiveData<Int?>()
    val meal : LiveData<Int?>
        get() = _meal

//    var meal = ObservableField<Int>(0)
    var veggie = ObservableField<Boolean>(false)
    var fish = ObservableField<Boolean>(false)


}
