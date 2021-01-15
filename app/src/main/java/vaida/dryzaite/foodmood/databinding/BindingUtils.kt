package vaida.dryzaite.foodmood.databinding

import android.annotation.SuppressLint
import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.chip.Chip
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.utilities.convertNumericMealTypeToString
import java.util.Locale

//Binding adapters to format DB data to UI
// also to format data from API

// formatting textView with DB data
@SuppressLint("SetTextI18n")
@BindingAdapter("mealTypeFormatted")
fun TextView.setMealTypeFormatted(recipe: RecipeEntry?) {
    recipe?.let {
        text = "Suggestion for \n ${
            convertNumericMealTypeToString(recipe.meal, context.resources).toLowerCase(
            Locale.ROOT)}"
    }
}

//simple converter int to string for Meal type LOWERCASE
@SuppressLint("SetTextI18n")
@BindingAdapter("mealTypeText")
fun TextView.setMealTypeTextFormatted(recipe: RecipeEntry?) {
    recipe?.let {
        text = convertNumericMealTypeToString(recipe.meal, context.resources).toLowerCase(
            Locale.ROOT)
    }
}

//simple converter int to string for Meal type Regular
@SuppressLint("SetTextI18n")
@BindingAdapter("mealTypeTextRegular")
fun TextView.setMealTypeText(mealType: Int?) {
    mealType?.let {
        text = convertNumericMealTypeToString(mealType, context.resources)
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
            5 -> R.drawable.ic_5
            else -> R.drawable.ic_food
        })
    }
}

// to use Glide to show images from url
@BindingAdapter("externalImage")
fun ImageView.bindImageThumbnail(imgUrl: String?) {
    imgUrl?.let {
        val imgUri = imgUrl.toUri().buildUpon().scheme("http").build()
        Glide.with(this)
            .load(imgUri)
            .centerCrop()
            .apply(RequestOptions()
                .placeholder(R.drawable.loading_animation)
                .error(R.drawable.ic_food))
            .into(this)
    }
}

// shows thumbnail or meal type placeholder
@BindingAdapter("imageUrl")
fun ImageView.bindImage(recipe: RecipeEntry?) {
    recipe?.let {
        if (it.thumbnail.isNullOrBlank()) {
            this.setMealTypeIcon(it)
        } else {
            val image = it.thumbnail ?: "placeholder"
            this.bindImageThumbnail(image)
        }
    }
}

@SuppressLint("SetTextI18n")
@BindingAdapter("mealTypeTextChip")
fun Chip.bindChip(mealType: Int?) {
    mealType?.let {
        text = convertNumericMealTypeToString(mealType, context.resources)
    }
}



