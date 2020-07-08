package vaida.dryzaite.foodmood.utilities

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import vaida.dryzaite.foodmood.R
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

//simple converter int to string for Meal type
@SuppressLint("SetTextI18n")
@BindingAdapter("mealTypeText")
fun TextView.setMealTypeTextFormatted(recipe: RecipeEntry?) {
    recipe?.let {
        text = convertNumericMealTypeToString(recipe.meal, context.resources).toLowerCase(
            Locale.ROOT)
    }
}

// converter to grab correct food icon
@BindingAdapter("mealTypeIcon")
fun ImageView.setMealTypeIcon(recipe: RecipeEntry?) {
    recipe?.let {
        setImageResource(when (recipe.meal) {
            1 -> R.drawable.ic_1
            2 -> R.drawable.ic_2
            3 -> R.drawable.ic_3
            4 -> R.drawable.ic_4
            else -> R.drawable.ic_5
        })
    }
}

