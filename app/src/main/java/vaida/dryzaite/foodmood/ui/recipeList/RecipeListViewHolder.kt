package vaida.dryzaite.foodmood.ui.recipeList

import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_recipe_view_holder.view.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.ui.homePage.HomeFragmentDirections
import vaida.dryzaite.foodmood.ui.recipePage.RecipeFragment
import vaida.dryzaite.foodmood.utilities.ItemSelectedListener

class RecipeListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener, ItemSelectedListener {

    private lateinit var recipe: RecipeEntry

    init {
        itemView.setOnClickListener(this)
    }

    //retrieving data avoiding findById
    fun bind(recipe: RecipeEntry) {
        this.recipe = recipe
        val context = itemView.context
        itemView.meal_icon.setImageResource(context.resources.getIdentifier(recipe.thumbnail(recipe.meal), null, context.packageName))
        itemView.recipe_title.text = recipe.title
    }

    // implicit intent- links to ext URL
    // // recipe page -
    override fun onClick(view: View) {
//        findNavController().navigate(RecipeListFragmentDirections.action_recipe_list_fragment_to_recipe_Fragment(recipe))
        val url = recipe.recipe
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        view.context.startActivity(intent)
        }

    // adding on and off background colors on dragged item
    override fun onItemSelected() {
        itemView.list_item_container.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorPrimaryDark))
    }

    override fun onItemCleared() {
        itemView.list_item_container.setBackgroundColor(0)
    }


}








