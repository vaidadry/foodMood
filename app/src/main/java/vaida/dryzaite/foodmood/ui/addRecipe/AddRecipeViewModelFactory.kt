package vaida.dryzaite.foodmood.ui.addRecipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vaida.dryzaite.foodmood.model.RecipeGenerator
import vaida.dryzaite.foodmood.model.room.RecipeDao


class AddRecipeViewModelFactory (private val generator: RecipeGenerator = RecipeGenerator(), val database: RecipeDao)
    : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddRecipeViewModel::class.java)) {
                return AddRecipeViewModel(generator, database) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}