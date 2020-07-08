package vaida.dryzaite.foodmood.ui.recipeList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_recipe_view_holder.view.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.ListItemRecipeViewHolderBinding
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.utilities.ItemSelectedListener

class RecipeListViewHolder(val binding: ListItemRecipeViewHolderBinding) : RecyclerView.ViewHolder(binding.root), ItemSelectedListener {

    // binding data to list item layout
    fun bind(recipe: RecipeEntry, clickListener: RecipeListOnClickListener) {
        binding.dataclassRecipeEntry = recipe
        binding.clickListener = clickListener

    }

    // adding on and off background colors on dragged item
    override fun onItemSelected() {
        itemView.list_item_container.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorPrimaryDark))
    }

    override fun onItemCleared() {
        itemView.list_item_container.setBackgroundColor(0)
    }

    // for RW item layout binding
    companion object {
        fun from(parent: ViewGroup): RecipeListViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = ListItemRecipeViewHolderBinding.inflate(layoutInflater, parent, false)
            return RecipeListViewHolder(binding)
        }
    }


}








