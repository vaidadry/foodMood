package vaida.dryzaite.foodmood.ui.addRecipe

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vaida.dryzaite.foodmood.model.RecipeGenerator
import vaida.dryzaite.foodmood.network.ExternalRecipe
//
//class AddRecipeViewModelFactory2 (private val generator: RecipeGenerator = RecipeGenerator(),
//                                 private val application: Application,
//                                 private val externalRecipe: ExternalRecipe? = null)
//    : ViewModelProvider.Factory {
//    @Suppress("unchecked_cast")
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(AddRecipeViewModel2::class.java)) {
//            return AddRecipeViewModel2(generator, application, externalRecipe) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//}