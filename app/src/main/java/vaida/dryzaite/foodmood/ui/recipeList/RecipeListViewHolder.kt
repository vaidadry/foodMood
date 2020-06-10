package vaida.dryzaite.foodmood.ui.recipeList

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_recipe_view_holder.view.*
import vaida.dryzaite.foodmood.model.RecipeEntry

class RecipeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var recipe: RecipeEntry

    //retrieving data avoiding findbyId
    fun bind(recipe: RecipeEntry) {
        this.recipe = recipe
        val context = itemView.context
        itemView.mealIcon.setImageResource(context.resources.getIdentifier(recipe.thumbnail(recipe.meal), null, context.packageName))
        itemView.recipeTitle.text = recipe.title
    }
}








