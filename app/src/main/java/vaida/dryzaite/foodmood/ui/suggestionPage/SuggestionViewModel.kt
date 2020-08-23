package vaida.dryzaite.foodmood.ui.suggestionPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.repository.RecipeRepository

class SuggestionViewModel(randomId: String, application: Application): AndroidViewModel(application) {


    private val repository: RecipeRepository = Injection.provideRecipeRepository(application)

    private var randomRecipe: LiveData<RecipeEntry>

    init {
        randomRecipe = repository.getRecipeWithId(randomId)
    }

    fun getRandomRecipe() = randomRecipe


    private val _navigateToUrl = MutableLiveData<String?>()
    val navigateToUrl: LiveData<String?>
    get() = _navigateToUrl



    fun onButtonClick() {
        _navigateToUrl.value = randomRecipe.value?.recipe
    }

    fun onButtonClicked() {
        _navigateToUrl.value = null
    }

}