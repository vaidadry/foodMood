package vaida.dryzaite.foodmood.model

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test

//adding TDD (test driven development), first provide with failing test, then with passing, then refactor
class RecipeGeneratorTest {
    private lateinit var recipeGenerator: RecipeGenerator

    @Before
    fun setup() {
        recipeGenerator = RecipeGenerator()
    }

    @Test
    fun generateRecipe() {
        //GIVEN
        val id = "0de39ad2-4ad0-4fb4-982c-7030d474c576"
        val date = "2020-06-08  15:53"
        val title = "kebabas"
        val veggie = true
        val fish = false
        val meal = 1
        val recipe = "www.example.com"
        val isFavorite = false
        val ingredients = ""

        val expectedRecipe = RecipeEntry(id, date, title, veggie, fish, meal, recipe, isFavorite, ingredients)
        //WHEN
        val generatedRecipe = recipeGenerator.generateRecipe(title, veggie, fish, meal, recipe)

        //THEN
        // UUID is generated each time separately as well as date logged, all else must be identical
        assertThat(expectedRecipe.id).isNotEqualTo(generatedRecipe.id)
        assertThat(expectedRecipe.date).isNotEqualTo(generatedRecipe.date)
        assertThat(expectedRecipe.meal).isEqualTo(generatedRecipe.meal)
        assertThat(expectedRecipe.title).isEqualTo(generatedRecipe.title)
        assertThat(expectedRecipe.veggie).isEqualTo(generatedRecipe.veggie)
        assertThat(expectedRecipe.fish).isEqualTo(generatedRecipe.fish)
        assertThat(expectedRecipe.recipe).isEqualTo(generatedRecipe.recipe)
        assertThat(expectedRecipe.isFavorite).isEqualTo(generatedRecipe.isFavorite)
        assertThat(expectedRecipe.ingredients).isEqualTo(generatedRecipe.ingredients)
    }

}