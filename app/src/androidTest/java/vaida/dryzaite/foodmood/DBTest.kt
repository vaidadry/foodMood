package vaida.dryzaite.foodmood

import androidx.room.Room
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Assert.*
import org.junit.Before
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.roomRecipeBook.RecipeDao
import vaida.dryzaite.foodmood.model.roomRecipeBook.RecipeDatabase
import java.io.IOException
import java.lang.Exception


@RunWith(AndroidJUnit4::class)
class RecipeDatabaseTest {

    private lateinit var recipeDao: RecipeDao
    private lateinit var db: RecipeDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, RecipeDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        recipeDao = db.recipeDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }


    @Test
    @Throws(Exception::class)
    fun insertRecipe() {
        val recipe = RecipeEntry(
            id = "0de39ad2-4ad0-4fb4-982c-7030d474c576",
            date = "2020-06-10  15:53",
            title = "kebabas",
            veggie = true,
            fish = false,
            meal = 1,
            recipe = "www.example.com",
            isFavorite = true)
        recipeDao.insertRecipe(recipe)
        val retrievedRecipe = recipeDao.getRecipeWithId("0de39ad2-4ad0-4fb4-982c-7030d474c576")
        assertEquals(retrievedRecipe.value?.isFavorite, true)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetAllRecipes() {
        val recipe1 = RecipeEntry(
            id = "0de39ad2-4ad0-4fb4-982c-7030d474c576",
            date = "2020-06-08  15:53",
            title = "kebabas",
            veggie = true,
            fish = false,
            meal = 1,
            recipe = "www.example.com",
            isFavorite = false)
        recipeDao.insertRecipe(recipe1)

        val recipe2 = RecipeEntry(
            id = "0de40ad2-4ad0-4fb4-982c-7030d474c576",
            date = "2020-07-08  15:53",
            title = "kebabas2",
            veggie = true,
            fish = true,
            meal = 2,
            recipe = "www.example.com",
            isFavorite = true)
        recipeDao.insertRecipe(recipe2)

        val recipes = recipeDao.getAllRecipes()
        assertEquals(recipes.value?.size, 2)
    }

    @Test
    @Throws
    fun getAndUpdateRecipeByKey() {
        val recipe = RecipeEntry(id = "0de39rd2-4adr-4fb4-982c-7030d474c576",
            date = "2020-06-09  15:53",
            title = "kebabas",
            veggie = true,
            fish = false,
            meal = 3,
            recipe = "www.example.com",
            isFavorite = false)
        recipeDao.insertRecipe(recipe)
        val latestRecipe = recipeDao.getRecipeWithId("0de39rd2-4adr-4fb4-982c-7030d474c576")
        latestRecipe.value?.isFavorite = true
        val updatedRecipe = recipeDao.updateRecipe(latestRecipe.value!!)
        assertNotEquals(latestRecipe, updatedRecipe)
        assertEquals(latestRecipe?.value!!.isFavorite, true)
    }

    @Test
    @Throws
    fun deleteRecipe() {
        val recipe = RecipeEntry(id = "0de39rd2-4adr-4fb4-982c-7030d474c576",
            date = "2020-06-09  15:53",
            title = "kebabas",
            veggie = true,
            fish = false,
            meal = 3,
            recipe = "www.example.com",
            isFavorite = false)
        recipeDao.insertRecipe(recipe)
        val recipeToDelete = recipeDao.getRecipeWithId("0de39rd2-4adr-4fb4-982c-7030d474c576")
        if (recipeToDelete != null) {
            recipeDao.deleteRecipe(recipeToDelete.value!!)
            assertEquals(recipeToDelete, null)
        }
    }
}
