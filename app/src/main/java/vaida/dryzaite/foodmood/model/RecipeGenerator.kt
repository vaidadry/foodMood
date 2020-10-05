package vaida.dryzaite.foodmood.model

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class RecipeGenerator @Inject constructor() {

    fun generateRecipe(title: String, veggie: Boolean = false, fish: Boolean = false, meal: Int, recipe: String, ingredients: String = ""): RecipeEntry {
        val id = UUID.randomUUID().toString()
        val date = logDateTime()
        val favorites = false
        return RecipeEntry(id, date, title, veggie, fish, meal, recipe, favorites, ingredients)
    }

    @SuppressLint("SimpleDateFormat")
    private fun logDateTime(): String {
        return SimpleDateFormat("yyyy-MM-dd HH:mm").format(Date())
    }
}