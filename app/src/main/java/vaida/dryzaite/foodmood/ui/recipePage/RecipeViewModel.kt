package vaida.dryzaite.foodmood.ui.recipePage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.room.RecipeRepository

class RecipeViewModel (keyId: String, application: Application): AndroidViewModel(application) {

    private val repository: RecipeRepository = Injection.provideRecipeRepository(application)

    private val detailRecipe: LiveData<RecipeEntry>

    init {
        detailRecipe = repository.getRecipeWithId(keyId)
    }

    fun getDetailRecipe() = detailRecipe

    //for use of UI
    val recipeDetail = getDetailRecipe()
}