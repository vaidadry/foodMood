package vaida.dryzaite.foodmood.ui.recipeList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_recipe_view_holder.view.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.ListItemRecipeViewHolderBinding
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.utilities.ItemSelectedListener
import vaida.dryzaite.foodmood.utilities.ItemTouchHelperListener
import java.util.Collections

class RecipeListAdapter(
    private val listener: RecipeListAdapterListener,
    private val clickListener: RecipeListOnClickListener)
    : RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>(), ItemTouchHelperListener{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemRecipeViewHolderBinding.inflate(layoutInflater, parent, false)
        return RecipeListViewHolder(binding)
    }

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        holder.bind(recipes[position], clickListener)
    }

    override fun onItemDismiss(viewHolder: RecyclerView.ViewHolder, position: Int) {
        listener.deleteRecipeAtPosition(recipes[position])
    }

    // interface method implemented in adapter
    override fun onItemMove(recyclerView: RecyclerView, fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(recipes, i, i + 1)
                }
            } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(recipes, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    private val diffCallback = object : DiffUtil.ItemCallback<RecipeEntry>() {
        override fun areItemsTheSame(oldItem: RecipeEntry, newItem: RecipeEntry): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: RecipeEntry, newItem: RecipeEntry): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)

    var recipes: List<RecipeEntry>
        get() = differ.currentList
        set(value) = differ.submitList(value)

    inner class RecipeListViewHolder(val binding: ListItemRecipeViewHolderBinding) : RecyclerView.ViewHolder(binding.root),
        ItemSelectedListener {

        fun bind(recipe: RecipeEntry, clickListener: RecipeListOnClickListener) {
            binding.dataclassRecipeEntry = recipe
            binding.clickListener = clickListener

            // click listener to change DB according to fav clicks
            binding.favorite.isChecked = recipe.isFavorite
            binding.favorite.setOnCheckedChangeListener { _, _ ->
                if (binding.favorite.isShown) {
                    if (binding.favorite.isChecked) {
                        listener.addFavorites(recipe)
                    } else {
                        listener.removeFavorites(recipe)
                    }
                }
            }
            binding.executePendingBindings()
        }

        // adding on and off background colors on dragged item
        override fun onItemSelected() {
            itemView.list_item_container.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorPrimaryDark))
        }

        override fun onItemCleared() {
            itemView.list_item_container.setBackgroundColor(0)
        }
    }

    // custom interface for listener to delete item at certain position
    interface RecipeListAdapterListener {
        fun deleteRecipeAtPosition(recipe: RecipeEntry)
        fun addFavorites(recipe: RecipeEntry)
        fun removeFavorites(recipe: RecipeEntry)
    }
}

//defining click listeners to respond to clicks on RW
open class RecipeListOnClickListener(val clickListener: (recipe: RecipeEntry) -> Unit) {
    fun onClick(recipe: RecipeEntry) = clickListener(recipe)
}



