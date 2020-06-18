package vaida.dryzaite.foodmood.model

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.util.*


//adding TDD (test driven development), first provide with failing test, then with passing, then refactor
class RecipeGeneratorTest {
    private lateinit var recipeGenerator: RecipeGenerator

    @Before
    fun setup() {
        recipeGenerator = RecipeGenerator()
    }

    @Test
    fun testGenerateRecipe() {
        val id = "0de39ad2-4ad0-4fb4-982c-7030d474c576"
        val date = "2020-06-08  15:53"
        val title = "kebabas"
        val veggie = true
        val fish = false
        val meal = "dinner"
        val recipe = "www.example.com"
        val favorites = false

        val expectedRecipe = RecipeEntry(id, date, title, veggie, fish, meal, recipe, favorites)

        //test now fails as UUID is generated each time separately as well as date logged
        assertEquals(expectedRecipe, recipeGenerator.generateRecipe(title, veggie, fish, meal, recipe))
    }

}