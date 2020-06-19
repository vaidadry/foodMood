package vaida.dryzaite.foodmood.viewmodel

import android.util.Log
import androidx.lifecycle.*
import io.reactivex.Completable
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.RecipeRepository


// ViewModel for recipeList fragment interacts with data from the repository
class RecipeListViewModel() : ViewModel() {

    private val repository: RecipeRepository =  Injection.provideRecipeRepository()

    private val allRecipesLiveData = repository.getAllRecipes()

    fun getAllRecipesLiveData() = allRecipesLiveData

    fun deleteRecipe(recipe: RecipeEntry) = repository.deleteRecipe(recipe)



    val allRecipes = MediatorLiveData<List<RecipeEntry>>()

    val originalResults: MutableList<RecipeEntry> = mutableListOf()
    val filteredResults: MutableList<RecipeEntry> = mutableListOf()
    val oldFilteredResults: MutableList<RecipeEntry> = mutableListOf()


    init {
        allRecipes.addSource(allRecipesLiveData) { result ->
            result?.let { recipe ->
                originalResults.addAll(recipe)
            }
            Log.i("original results", "$originalResults")
        }

        oldFilteredResults.addAll(originalResults)
        Log.i("old filter results", "$oldFilteredResults")
    }


    fun search(query: String): Completable = Completable.create{
        Log.i("search input", " searched: $query, ofr: $oldFilteredResults")
        val wanted = originalResults.filter {recipe ->
            recipe.title.contains(query) || recipe.meal.contains(query)
        }.toMutableList()
        Log.i("search output", "$wanted")

        filteredResults.clear()
        filteredResults.addAll(wanted)
        Log.i("filtered results", "$filteredResults")
        it.onComplete()

    }



}