package vaida.dryzaite.foodmood.ui.discoverRecipes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class DiscoverRecipesViewModelFactory() : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DiscoverRecipesViewModel::class.java)) {
            return DiscoverRecipesViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}