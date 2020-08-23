package vaida.dryzaite.foodmood.utilities

import androidx.recyclerview.widget.DiffUtil
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.network.ExternalRecipe


//callback measures the differences between changes and makes RecyclerView updates nicer
class RecipeDiffCallback(private val oldRecipes: List<RecipeEntry>, private val newRecipes: List<RecipeEntry>)
    : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldRecipes[oldItemPosition].id == newRecipes[newItemPosition].id
    }

    override fun getOldListSize(): Int = oldRecipes.size

    override fun getNewListSize(): Int = newRecipes.size

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldRecipe = oldRecipes[oldItemPosition]
        val newRecipe = newRecipes[newItemPosition]

        return oldRecipe.id == newRecipe.id
    }
}

//NOT USED CURRENTLY

////callback measures the differences between changes and makes RecyclerView updates nicer
//class ExternalRecipesDiffCallback(private val oldRecipes: List<ExternalRecipe>, private val newRecipes: List<ExternalRecipe>)
//    : DiffUtil.Callback() {
//
//    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        return oldRecipes[oldItemPosition].href == newRecipes[newItemPosition].href
//    }
//
//    override fun getOldListSize(): Int = oldRecipes.size
//
//    override fun getNewListSize(): Int = newRecipes.size
//
//    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
//        val oldRecipe = oldRecipes[oldItemPosition]
//        val newRecipe = newRecipes[newItemPosition]
//
//        return oldRecipe.title == newRecipe.title
//    }
//}

