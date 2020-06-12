package vaida.dryzaite.foodmood.ui.recipeList

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_recipe_view_holder.view.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.ui.main.MainActivity
import vaida.dryzaite.foodmood.utilities.isValidUrl
import java.io.IOException

class RecipeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

    private lateinit var recipe: RecipeEntry

    init {
        itemView.setOnClickListener(this)
    }

    //retrieving data avoiding findById
    fun bind(recipe: RecipeEntry) {
        this.recipe = recipe
        val context = itemView.context
        itemView.mealIcon.setImageResource(context.resources.getIdentifier(recipe.thumbnail(recipe.meal), null, context.packageName))
        itemView.recipeTitle.text = recipe.title
    }

    // implicit intent- links to recipe URL -
    override fun onClick(view: View) {
        val url = recipe.recipe
        if (url.isValidUrl()) {
            val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse(recipe.recipe))
            view.context.startActivity(webIntent)
        }
        else {
            Toast.makeText(itemView.context, R.string.error_opening_recipe, Toast.LENGTH_SHORT).show()
        }

    }

}








