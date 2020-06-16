package vaida.dryzaite.foodmood.viewmodel

import android.util.Log
import androidx.lifecycle.*
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.RecipeGenerator
import vaida.dryzaite.foodmood.model.RecipeRepository


// ViewModel for Suggestion fragment
class SuggestionViewModel() : ViewModel() {

    private val repository: RecipeRepository = Injection.provideRecipeRepository()

    private val allRecipesLiveData = repository.getAllRecipes()

    fun getAllRecipesLiveData() = allRecipesLiveData
//
    private val randomRecipeLiveData = MutableLiveData<RecipeEntry>()
//
//
////    var title = ""
////    var veggie = false
////    var fish = false
////    var meal = ""
////    var recipe = ""
//
////
//    fun generateRandomRecipe(): RecipeEntry {
//        Transformations.map(allRecipesLiveData) { recipes ->
//            recipes?.let {
//                val random = recipes.random()
//            }
//        }
//    return random
//    }
//


//    val randomRecipe: RecipeEntry
//    get() = Transformations.switchMap(allRecipesLiveData) { recipe ->
//        val randomEntry = when {
//            recipe == null -> TODO()
//            else -> {
//                Transformations.switchMap(allRecipesLiveData) {
//                    val randomRecipe = it.random()
//
//                }
//            }
//        }
//
//    }
//
//

//
//    lateinit var entry: RecipeEntry
//
//    //    val array = allRecipesLiveData
//    fun generateRandomEntry(): LiveData<RecipeEntry> {
//        return Transformations.map(allRecipesLiveData) {
//            it.random()
//        }
//    }
}






