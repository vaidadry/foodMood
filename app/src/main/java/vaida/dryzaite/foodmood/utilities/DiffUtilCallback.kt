package vaida.dryzaite.foodmood.utilities

import androidx.recyclerview.widget.DiffUtil
import vaida.dryzaite.foodmood.model.RecipeEntry


//callback measures the differences between changes
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

        return oldRecipe.date == newRecipe.date &&
                oldRecipe.title == newRecipe.title &&
                oldRecipe.veggie == newRecipe.veggie &&
                oldRecipe.fish == newRecipe.fish &&
                oldRecipe.meal == newRecipe.meal &&
                oldRecipe.recipe == newRecipe.recipe


    }
}

