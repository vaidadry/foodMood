package vaida.dryzaite.foodmood.ui.recipeList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.utilities.ItemTouchHelperListener
import vaida.dryzaite.foodmood.utilities.RecipeDiffCallback
import java.util.*

class RecipeListAdapter(private val recipes: MutableList<RecipeEntry>, private val listener: RecipeListAdapterListener)
    : RecyclerView.Adapter<RecipeListViewHolder>(), ItemTouchHelperListener {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_recipe_view_holder, parent, false)
    return RecipeListViewHolder(view)
    }

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        holder.bind(recipes[position])
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
//        recipes.removeAt(position)
//        notifyItemRemoved(position)
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

    // custom interface for listener to delete item at certain position
    interface RecipeListAdapterListener {
        fun deleteRecipeAtPosition(recipe: RecipeEntry)
    }

}





