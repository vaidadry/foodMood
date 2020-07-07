package vaida.dryzaite.foodmood.ui.suggestionPage

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.utilities.convertNumericMealTypeToString
import java.util.*

//Binding adapters to format DB data to UI

@SuppressLint("SetTextI18n")
@BindingAdapter("mealTypeFormatted")
fun TextView.setMealTypeFormatted(recipe: RecipeEntry?) {
    recipe?.let {
        text = "Suggestion for \n ${convertNumericMealTypeToString(recipe.meal, context.resources).toLowerCase(
            Locale.ROOT)}"
    }
}

