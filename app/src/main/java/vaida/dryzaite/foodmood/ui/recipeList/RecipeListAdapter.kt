package vaida.dryzaite.foodmood.ui.recipeList

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.list_item_recipe_view_holder.view.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.ListItemRecipeViewHolderBinding
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.utilities.ItemSelectedListener
import vaida.dryzaite.foodmood.utilities.ItemTouchHelperListener
import vaida.dryzaite.foodmood.utilities.RecipeDiffCallback
import java.util.*
import kotlin.collections.ArrayList

class RecipeListAdapter(
    private val recipes: MutableList<RecipeEntry>,
    private val listener: RecipeListAdapterListener,
    private val clickListener: RecipeListOnClickListener)
    : RecyclerView.Adapter<RecipeListAdapter.RecipeListViewHolder>(), ItemTouchHelperListener, Filterable {

    var recipeFilterList = ArrayList<RecipeEntry>()

    init {
        recipeFilterList = recipes as ArrayList<RecipeEntry>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ListItemRecipeViewHolderBinding.inflate(layoutInflater, parent, false)
        return RecipeListViewHolder(binding)
    }

    override fun getItemCount() = recipeFilterList.size

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        holder.bind(recipeFilterList[position], clickListener)
    }

    fun updateRecipes(recipes: List<RecipeEntry>) {
        //implementing diff callback to calculate differences and send updates to adapter
        val diffCallback = RecipeDiffCallback(this.recipes, recipes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.recipes.clear()
        this.recipes.addAll(recipes)

        diffResult.dispatchUpdatesTo(this)
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

    // filter/search by title
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                recipeFilterList = if (charSearch.isEmpty()) {
                    recipes as ArrayList<RecipeEntry>
                } else {
                    val resultList = ArrayList<RecipeEntry>()
                    for (entry in recipes) {
                        if (entry.title.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(entry)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = recipeFilterList
                return filterResults
            }
            @Suppress ("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                recipeFilterList = results?.values as ArrayList<RecipeEntry>
                notifyDataSetChanged()
            }
        }
    }


    inner class RecipeListViewHolder(val binding: ListItemRecipeViewHolderBinding) : RecyclerView.ViewHolder(binding.root),
        ItemSelectedListener {

        // binding data to list item layout
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
open class RecipeListOnClickListener(val clickListener: (id: String) -> Unit) {
    fun onClick(recipe: RecipeEntry) = clickListener(recipe.id)
}



