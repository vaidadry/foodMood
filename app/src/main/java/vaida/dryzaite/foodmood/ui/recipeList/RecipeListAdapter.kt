package vaida.dryzaite.foodmood.ui.recipeList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.model.RecipeEntry

class RecipeListAdapter(private val recipes: List<RecipeEntry>): RecyclerView.Adapter<RecipeListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeListViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item_recipe_view_holder, parent, false)
    return RecipeListViewHolder(view)
    }

    override fun getItemCount() = recipes.size

    override fun onBindViewHolder(holder: RecipeListViewHolder, position: Int) {
        holder.bind(recipes[position])
    }
}