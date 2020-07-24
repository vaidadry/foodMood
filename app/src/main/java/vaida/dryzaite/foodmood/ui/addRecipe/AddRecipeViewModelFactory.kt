package vaida.dryzaite.foodmood.ui.addRecipe

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vaida.dryzaite.foodmood.model.RecipeGenerator
import vaida.dryzaite.foodmood.model.room.RecipeDao
import vaida.dryzaite.foodmood.network.ExternalRecipe


class AddRecipeViewModelFactory (private val generator: RecipeGenerator = RecipeGenerator(),
                                 private val application: Application)
    : ViewModelProvider.Factory {
        @Suppress("unchecked_cast")
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddRecipeViewModel::class.java)) {
                return AddRecipeViewModel(generator, application) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}