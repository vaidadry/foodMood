package vaida.dryzaite.foodmood.ui.recipeList

import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.utilities.ItemTouchHelperListener
import vaida.dryzaite.foodmood.utilities.RecipeDiffCallback
import java.util.*
import kotlin.collections.ArrayList

class RecipeListAdapter(private val recipes: MutableList<RecipeEntry>, private val listener: RecipeListAdapterListener, private val clickListener: RecipeListOnClickListener)
    : RecyclerView.Adapter<RecipeListViewHolder>(), ItemTouchHelperListener, Filterable {

    var recipeFilterList = ArrayList<RecipeEntry>()

    init {
        recipeFilterList = recipes as ArrayList<RecipeEntry>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        return RecipeListViewHolder.from(parent)
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
            }else {
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

    // custom interface for listener to delete item at certain position
    interface RecipeListAdapterListener {
        fun deleteRecipeAtPosition(recipe: RecipeEntry)
    }
}

//defining click listeners to respond to clicks on RW
class RecipeListOnClickListener(val clickListener: (id: String) -> Unit) {
    fun onClick(recipe: RecipeEntry) = clickListener(recipe.id)
}



