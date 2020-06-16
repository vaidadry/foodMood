package vaida.dryzaite.foodmood.ui.recipeList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.utilities.ItemTouchHelperListener
import java.util.*

class RecipeListAdapter(private val recipes: MutableList<RecipeEntry>): RecyclerView.Adapter<RecipeListViewHolder>(), ItemTouchHelperListener {


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
        this.recipes.clear()
        this.recipes.addAll(recipes)
        notifyDataSetChanged()
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

}



