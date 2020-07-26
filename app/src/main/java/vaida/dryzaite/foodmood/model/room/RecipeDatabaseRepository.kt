package vaida.dryzaite.foodmood.model.room

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import vaida.dryzaite.foodmood.model.RecipeEntry
import java.lang.reflect.Array.get
import kotlin.coroutines.CoroutineContext


//Repository integrated with coroutines to send work off main thread
class RecipeDatabaseRepository(application: Application) : RecipeRepository, CoroutineScope {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private val recipeDao: RecipeDao?

    private val allRecipes: LiveData<List<RecipeEntry>>
    private val filteredRecipes: LiveData<List<RecipeEntry>>

    init {
        val database = RecipeDatabase.getInstance(application)
        recipeDao = database.recipeDao
        allRecipes = recipeDao.getAllRecipes()
        filteredRecipes = recipeDao.getFilteredRecipes(0)
    }

    //    When we get the recipes, we are already returning LiveData in Dao.
//    So we don’t need to get the data from background thread.
    override fun getAllRecipes(): LiveData<List<RecipeEntry>> = allRecipes


    override fun insertRecipe(recipe: RecipeEntry) {
        launch { insertRecipeBG(recipe) }
    }

    private suspend fun insertRecipeBG(recipe: RecipeEntry) {
        withContext(Dispatchers.IO) {
            recipeDao?.insertRecipe(recipe)
        }
    }

    override fun deleteRecipe(recipe: RecipeEntry) {
        launch { deleteRecipeBG(recipe) }
    }

    private suspend fun deleteRecipeBG(recipe: RecipeEntry) {
        withContext(Dispatchers.IO) {
            recipeDao?.deleteRecipe(recipe)
        }
    }

    override fun updateRecipe(recipe: RecipeEntry) {
        launch { updateRecipeBG(recipe) }
    }

    private suspend fun updateRecipeBG(recipe: RecipeEntry) {
        withContext(Dispatchers.IO) {
            recipeDao?.updateRecipe(recipe)
        }
    }

    override fun getRecipeWithId(key: String): LiveData<RecipeEntry> {
        return recipeDao!!.getRecipeWithId(key)
    }

    override fun getFavorites(): LiveData<List<RecipeEntry>> {
        return recipeDao!!.getFavorites()
    }

    override fun getFilteredRecipes(meal: Int): LiveData<List<RecipeEntry>> {
        return  recipeDao!!.getFilteredRecipes(meal)
    }

}