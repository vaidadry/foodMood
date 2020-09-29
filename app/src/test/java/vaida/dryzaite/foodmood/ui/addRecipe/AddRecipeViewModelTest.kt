package vaida.dryzaite.foodmood.ui.addRecipe

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mockito.mock
import vaida.dryzaite.foodmood.MainCoroutineRule
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.RecipeGenerator
import vaida.dryzaite.foodmood.repository.RecipeRepository
import vaida.dryzaite.foodmood.utilities.isValidUrl
import vaida.dryzaite.foodmood.utilities.recipe1

//view model and live data test with a mock-up
//we need to add a test rule that swaps out the background thread executor normally used
//with a synchronous thread executor

@RunWith(JUnit4::class)
class AddRecipeViewModelTest {

    private lateinit var viewModel: AddRecipeViewModel

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    //using MAIN coroutine rule for before/after
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    //adding property for the mock creature generator for the viewmodel

    private val mockGenerator = mock(RecipeGenerator::class.java)

    //add mock repository and update setup method - no need if Injection is used
    private val repository = mock(RecipeRepository::class.java)


    //add a setup method annotated and in it we init the mocks and set up the view model to be tested
    @Before
    fun setup() {
        viewModel = AddRecipeViewModel(mockGenerator, repository)
    }


    //adding test for setting up the recipe being generated in the view model
    //first - arrange expected value as STUB,
    @Test
    fun saveNewRecipe_updatesData_verifiedAndInserted() {

        val stubRecipe = recipe1

        viewModel.title.set("title1")
        viewModel.veggie.set(false)
        viewModel.fish.set(false)
        viewModel.meal.set(1)
        viewModel.recipe.set("https://www.example1.com")

        val entry: RecipeEntry = mockGenerator.generateRecipe(
            viewModel.title.get() ?: "",
            viewModel.veggie.get() ?: false,
            viewModel.fish.get() ?: false,
            viewModel.meal.get() ?: 0,
            viewModel.recipe.get() ?: "",
            viewModel.ingredients.get() ?: ""
        )


        assertThat(stubRecipe.title).isEqualTo(entry.title)
        viewModel.saveNewRecipe()
        assertThat(stubRecipe.title).isEqualTo(viewModel.entry.title)
    }


    //now fails coz of URL validator, or canSaveRecipe()
    @Test
    fun canSaveRecipe_AllFilledCorrectly_Success() {

        viewModel.title.set("title1")
        viewModel.recipe.set("https://example.com")
        viewModel.fish.set(false)
        viewModel.veggie.set(false)
        viewModel.meal.set(1)

        val canSaveRecipe = viewModel.canSaveRecipe()
        assertThat(canSaveRecipe).isTrue()
    }


    @Test
    fun canSaveRecipe_BlankTitle_Fail() {
        viewModel.title.set("")
        viewModel.recipe.set("https://example.com")
        viewModel.fish.set(false)
        viewModel.veggie.set(false)
        viewModel.meal.set(1)

        val canSaveRecipe = viewModel.canSaveRecipe()
        assertThat(canSaveRecipe).isFalse()
    }

    @Test
    fun canSaveRecipe_BlankUrl_Fail() {
        viewModel.title.set("title1")
        viewModel.recipe.set("")
        viewModel.fish.set(false)
        viewModel.veggie.set(false)
        viewModel.meal.set(1)

        val canSaveRecipe = viewModel.canSaveRecipe()
        assertThat(canSaveRecipe).isFalse()
    }

    @Test
    fun canSaveRecipe_BlankMeal_Fail() {
        viewModel.title.set("test")
        viewModel.recipe.set("https://example.com")
        viewModel.fish.set(false)
        viewModel.veggie.set(false)
        viewModel.meal.set(0)

        val canSaveRecipe = viewModel.canSaveRecipe()
        assertThat(canSaveRecipe).isFalse()
    }

    @Test
    fun validateUrl_CorrectUrl_Success() {
        val url = viewModel.recipe.set("https://example.com").toString()
        val isValidUrl = url.isValidUrl()
        assertThat(isValidUrl).isTrue()
    }

    @Test
    fun validateUrl_IncorrectUrl_Fail() {
        val url = viewModel.recipe.set("example.com").toString()
        val isValidUrl = url.isValidUrl()
        assertThat(isValidUrl).isFalse()
    }


}
