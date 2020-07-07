package vaida.dryzaite.foodmood.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.RecipeGenerator
import vaida.dryzaite.foodmood.model.room.RecipeRepository
import vaida.dryzaite.foodmood.model.room.RecipeDao
import vaida.dryzaite.foodmood.ui.addRecipe.AddRecipeViewModel
import vaida.dryzaite.foodmood.utilities.isValidUrl

//view model and live data test with a mock-up
//we need to add a test rule that swaps out the background thread executor normally used
//with a synchronous thread executor

class RecipeViewModelTest {

    private lateinit var recipeViewModel: AddRecipeViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    //adding property for the mock creature generator for the viewmodel

    @Mock
    lateinit var mockGenerator: RecipeGenerator

     //add mock repository and update setup method - no need if Injection is used
    @Mock
    lateinit var repository: RecipeRepository
    lateinit var database: RecipeDao


    //add a setup method annotated and in it we init the mocks and set up the view model to be tested
    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        recipeViewModel =
            AddRecipeViewModel(mockGenerator, database)
    }


    //TEST IS FAILING COZ WE DONT YET USE LIVE DATA (?)
    //adding test for setting up the creature being generated in the view model
    //first - arrange expected value as STUB,
    @Test
    fun testSetupRecipe() {
        val id = "0de39ad2-4ad0-4fb4-982c-7030d474c576"
        val date = "2020-06-08  15:53"
        val title = "kebabas"
        val veggie = true
        val fish = false
        val meal = 1
        val recipe = "www.example.com"
        val favorites = false

        val stubRecipe = RecipeEntry(id, date, title, veggie, fish, meal, recipe, favorites)
        `when` (mockGenerator.generateRecipe(title, veggie, fish, meal, recipe)).thenReturn(stubRecipe)


        recipeViewModel.title.set("kebabas")
        recipeViewModel.recipe.set("www.example.com")
        recipeViewModel.meal.set(1)

        recipeViewModel.updateEntry()

        assertEquals(stubRecipe, recipeViewModel.recipe)
    }

    //test prevents of blanks
    @Test
    fun testCantSaveWithBlankTitle() {
        recipeViewModel.title.set("")
        recipeViewModel.recipe.set("test")
        recipeViewModel.fish.set(false)
        recipeViewModel.veggie.set(false)
        recipeViewModel.meal.set(1)

        val canSaveRecipe = recipeViewModel.canSaveRecipe()
        assertEquals(false, canSaveRecipe)
    }

    @Test
    fun testCantSaveWithBlankUrl() {
        recipeViewModel.title.set("test")
        recipeViewModel.recipe.set("")
        recipeViewModel.fish.set(false)
        recipeViewModel.veggie.set(false)
        recipeViewModel.meal.set(1)

        val canSaveRecipe = recipeViewModel.canSaveRecipe()
        assertEquals(false, canSaveRecipe)
    }

    @Test
    fun testCantSaveWithBlankMeal() {
        recipeViewModel.title.set("test")
        recipeViewModel.recipe.set("test")
        recipeViewModel.fish.set(false)
        recipeViewModel.veggie.set(false)
        recipeViewModel.meal.set(0)

        val canSaveRecipe = recipeViewModel.canSaveRecipe()
        assertEquals(false, canSaveRecipe)
    }

    @Test
    fun testUrlValidator() {
        val url = recipeViewModel.recipe.set("http://www.google.lt").toString()
        val isValidUrl = url.isValidUrl()
        assertEquals(true, isValidUrl)
    }


}
