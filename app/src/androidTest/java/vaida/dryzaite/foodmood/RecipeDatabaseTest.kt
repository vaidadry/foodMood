package vaida.dryzaite.foodmood

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.Before
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.database.RecipeDao
import vaida.dryzaite.foodmood.database.RecipeDatabase
import com.google.common.truth.Truth.assertThat
import org.junit.Rule
import javax.inject.Inject

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RecipeDatabaseTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    private lateinit var recipeDao: RecipeDao
    private lateinit var db: RecipeDatabase

    @Before
    fun createDb() {
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RecipeDatabase::class.java)
            .allowMainThreadQueries()
            .build()
        recipeDao = db.recipeDao()
    }

    @After
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertRecipe() = runBlockingTest {
        val recipe = recipe1
        recipeDao.insertRecipe(recipe)
        val retrievedRecipe = recipeDao.getRecipeWithId("0de39ad2-4ad0-4fb4-982c-7030d474c576").getOrAwaitValueAndroidTest()
        assertThat(retrievedRecipe.id).isEqualTo(recipe.id)
        assertThat(retrievedRecipe.title).isEqualTo(recipe.title)
        assertThat(retrievedRecipe.isFavorite).isTrue()
    }

    @Test
    fun insertAndGetAllRecipes() = runBlockingTest {
        val recipe1 = recipe1
        recipeDao.insertRecipe(recipe1)

        val recipe2 = recipe2
        recipeDao.insertRecipe(recipe2)
        val recipes = recipeDao.getAllRecipes().getOrAwaitValueAndroidTest()

        assertThat(recipes.size).isEqualTo(2)
    }

    @Test
    fun getAndUpdateRecipeByKey() = runBlockingTest {
        val recipe = recipe1
        recipeDao.insertRecipe(recipe)
        val latestRecipe = recipeDao.getRecipeWithId("0de39rd2-4adr-4fb4-982c-7030d474c576").getOrAwaitValueAndroidTest()
        latestRecipe.isFavorite = true
        val updatedRecipe = recipeDao.updateRecipe(latestRecipe)

        assertThat(latestRecipe).isNotEqualTo(updatedRecipe)
        assertThat(latestRecipe.isFavorite).isEqualTo(true)
    }

    @Test
    fun deleteRecipe() = runBlockingTest {
        val recipe = RecipeEntry(id = "0de39rd2-4adr-4fb4-982c-7030d474c576",
            date = "2020-06-09  15:53",
            title = "kebabas",
            veggie = true,
            fish = false,
            meal = 3,
            href = "www.example.com",
            isFavorite = false,
            ingredients = "")
        recipeDao.insertRecipe(recipe)
        val recipeToDelete = recipeDao.getRecipeWithId("0de39rd2-4adr-4fb4-982c-7030d474c576").getOrAwaitValueAndroidTest()
        recipeDao.deleteRecipe(recipeToDelete)

        assertThat(recipeToDelete).isNull()
    }
}
