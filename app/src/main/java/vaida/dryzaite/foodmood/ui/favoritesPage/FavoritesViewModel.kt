package vaida.dryzaite.foodmood.ui.favoritesPage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.room.RecipeRepository

class FavoritesViewModel(application: Application) : AndroidViewModel(application){

    private val repository: RecipeRepository = Injection.provideRecipeRepository(application)

    fun getFavorites() = repository.getFavorites()

    //updating database with changed status of favorites
    fun updateRecipe(recipe: RecipeEntry) = repository.updateRecipe(recipe)


    //defining navigation state
    private val _navigateToRecipeDetail = MutableLiveData<String>()
    val navigateToRecipeDetail
        get() = _navigateToRecipeDetail


    fun onRecipeClicked(id: String) {
        _navigateToRecipeDetail.value = id
    }

    ///and method to stop navigating
    fun onRecipeDetailNavigated() {
        _navigateToRecipeDetail.value = null
    }

}