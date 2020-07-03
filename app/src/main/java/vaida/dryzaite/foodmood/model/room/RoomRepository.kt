package vaida.dryzaite.foodmood.model.room

import android.os.AsyncTask
import androidx.lifecycle.LiveData
import vaida.dryzaite.foodmood.app.FoodmoodApplication
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.RecipeRepository

//class RoomRepository : RecipeRepository {
//
//     private val recipeDao: RecipeDao = FoodmoodApplication.database.recipeDao()
//    private val allRecipes: LiveData<List<RecipeEntry>>
//
//    init {
//        allRecipes = recipeDao.getAllRecipes()
//    }
//
//    override fun saveNewRecipe(recipeEntry: RecipeEntry) {
//        InsertAsyncTask(recipeDao).execute(recipeEntry)
//    }
//
//    override fun getAllRecipes(): LiveData<List<RecipeEntry>> = allRecipes
//
//    override fun deleteRecipe(recipeEntry: RecipeEntry) {
//        val recipeArray = allRecipes.value?.toTypedArray()
//        if (recipeArray != null) {
//            DeleteAsyncTask(recipeDao).execute(recipeEntry)
//        }
//    }
//
////!!!!
//    override fun updateRecipe(recipeEntry: RecipeEntry) {
//        TODO("Not yet implemented")
//    }
//
//    //to move insert task off the main thread, creating async task - improved behaviour when used with Live data
//    private class InsertAsyncTask internal constructor(private val dao: RecipeDao) : AsyncTask<RecipeEntry, Void, Void>() {
//        override fun doInBackground(vararg params: RecipeEntry): Void? {
//            dao.insertRecipe(params[0])
//            return null
//        }
//    }
//
//    //to move delete task off the main thread, creating async task - improved behaviour when used with Live data
//    private class DeleteAsyncTask internal constructor(private val dao: RecipeDao) : AsyncTask<RecipeEntry, Void, Void>() {
//        override fun doInBackground(vararg params: RecipeEntry): Void? {
//            dao.deleteRecipe(params[0])
//            return null
//        }
//    }
//}