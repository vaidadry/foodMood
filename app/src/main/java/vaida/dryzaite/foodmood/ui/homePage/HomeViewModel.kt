package vaida.dryzaite.foodmood.ui.homePage

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.room.RecipeRepository

class HomeViewModel(application: Application): AndroidViewModel(application) {

    private val repository: RecipeRepository = Injection.provideRecipeRepository(application)

    private val allRecipesLiveData= repository.getAllRecipes()
//    private val filteredRecipes =repository.getFilteredRecipes(meal.get() ?: 0)

    fun getFilteredRecipes(): LiveData<List<RecipeEntry>> = repository.getFilteredRecipes(mealx.value ?: 0)
    fun getAllRecipes() = allRecipesLiveData

    // "_" means that it is a backing property; in fragment only original ones must be used.

    private val _randomRecipe = MutableLiveData<RecipeEntry>()
    val randomRecipe: LiveData<RecipeEntry>
        get() = _randomRecipe


    private fun getRandomRecipe(): RecipeEntry? {
        Timber.i("veggie selection : ${veggie.get()}, fish selection: ${fish.get()}, meal selection: ${meal.get()}")
        val filteredRecipes = getFilteredRecipes()
        Timber.i("all recipes retrieved ${filteredRecipes.value}")
            if (!filteredRecipes.value.isNullOrEmpty()) {
                _randomRecipe.value = filteredRecipes.value?.random()
            } else  {
                _randomRecipe.value = null
            }
            Timber.i("randomly generated entry in viewModel: ${_randomRecipe.value}")
        return  _randomRecipe.value
//        if (!allRecipesLiveData.value.isNullOrEmpty()) {
//            _randomRecipe.value = allRecipesLiveData.value?.random()
//        } else  {
//            _randomRecipe.value = null
//        }
//        Timber.i("randomly generated entry in viewModel: ${_randomRecipe.value}")
//       return  _randomRecipe.value

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


    //handing meal radio selection and checkbox clicks:
    fun onSetMealType(mealSelection: Int) {
//        this.meal.set(mealSelection)
        _meal.value = mealSelection
    }

    private val _meal = MutableLiveData<Int>()
    val mealx : LiveData<Int>
        get() = _meal

    var meal = ObservableField<Int>(0)
    var veggie = ObservableField<Boolean>(false)
    var fish = ObservableField<Boolean>(false)


}