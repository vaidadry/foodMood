package vaida.dryzaite.foodmood.viewmodel

import androidx.lifecycle.ViewModel
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeRepository


// ViewModel for Suggestion fragment
class SuggestionViewModel() : ViewModel() {

    private val repository: RecipeRepository =  Injection.provideRecipeRepository()

    private val allRecipesLiveData = repository.getAllRecipes()


//        private fun generateRandomEntry() {
//        if (allRecipesLiveData.isNotEmpty()) {
//            val recipeSuggestion = allRecipesLiveData.random()
//            suggestionId = recipeEntry.id
//            Log.v(TAG, "meal generated: $suggestionId")
//        } else {
//            Toast.makeText(context, getString(R.string.no_recipe_available_toast), Toast.LENGTH_SHORT)
//                .show()
//        }
//    }
}