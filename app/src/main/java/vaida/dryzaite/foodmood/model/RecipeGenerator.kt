package vaida.dryzaite.foodmood.model

import java.text.SimpleDateFormat
import java.util.*

class RecipeGenerator {

    fun generateRecipe(title: String, veggie: Boolean = false, fish: Boolean = false, meal: String, recipe: String): RecipeEntry {
        val id = UUID.randomUUID().toString()
        val date = logDateTime()
        return RecipeEntry(id, date, title, veggie, fish, meal, recipe)
    }

    private fun logDateTime(): String {
        val formattedDate = SimpleDateFormat("yyyy-MM-dd").format(Date())
        val formattedTime = SimpleDateFormat("HH:mm").format(Date())
        return "$formattedDate $formattedTime"
    }
}