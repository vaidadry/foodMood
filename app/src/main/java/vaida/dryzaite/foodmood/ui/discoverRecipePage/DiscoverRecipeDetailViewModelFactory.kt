package vaida.dryzaite.foodmood.ui.recipePage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vaida.dryzaite.foodmood.network.ExternalRecipe
import vaida.dryzaite.foodmood.ui.discoverRecipePage.DiscoverRecipeDetailViewModel
import java.lang.IllegalArgumentException

class DiscoverRecipeDetailViewModelFactory(
    private val externalRecipe: ExternalRecipe,
    private val application: Application): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiscoverRecipeDetailViewModel::class.java)) {
            return DiscoverRecipeDetailViewModel(externalRecipe, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}