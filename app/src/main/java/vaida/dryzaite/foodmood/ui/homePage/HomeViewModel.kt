package vaida.dryzaite.foodmood.ui.homePage

import android.app.Application
import androidx.databinding.ObservableBoolean
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

//    private var allRecipesLiveData = repository.getAllRecipes()

    private val allRecipesLiveData= repository.getAllRecipes()

    fun getAllRecipes() = allRecipesLiveData

    // "_" means that it is backing property; in fragment only original ones must be used.

    private val _randomRecipe = MutableLiveData<RecipeEntry>()
    val randomRecipe: LiveData<RecipeEntry>
        get() = _randomRecipe


    private fun getRandomRecipe(): RecipeEntry? {
        Timber.i("all recipes retrieved ${allRecipesLiveData.value}")
        if (!allRecipesLiveData.value.isNullOrEmpty()) {
            _randomRecipe.value = allRecipesLiveData.value?.random()
        } else  {
            _randomRecipe.value = null
        }
        Timber.i("randomly generated entry in viewModel: ${_randomRecipe.value}")
       return  _randomRecipe.value

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


// handing meal selection clicks
    private val _meal = MutableLiveData<Int>()
    val meal:LiveData<Int>
        get() = _meal


    //parameter to observe state when meal is selected - nor finished!!!!
    private val _onMealSelected = MutableLiveData<Boolean?>()
    val onMealSelected: LiveData<Boolean?>
        get() = _onMealSelected


    fun onSetMealType(mealSelection: Int) {
        _meal.value = mealSelection
        _onMealSelected.value = true
    }

    fun mealSelectionCompleted() {
        _onMealSelected.value = null
    }

    var veggie = ObservableField<Boolean>(false)
    var fish = ObservableField<Boolean>(false)


}