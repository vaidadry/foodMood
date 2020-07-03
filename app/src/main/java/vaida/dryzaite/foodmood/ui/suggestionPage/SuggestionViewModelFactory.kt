package vaida.dryzaite.foodmood.ui.suggestionPage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import vaida.dryzaite.foodmood.model.room.RecipeDao
import java.lang.IllegalArgumentException


//can be used if there are multiple viewModels (?)
class SuggestionViewModelFactory(private val randomTitle: String,
                                 private val randomMeal: Int,
                                 private val randomUrl: String,
                                 private val dataSource: RecipeDao): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SuggestionViewModel::class.java)) {
            return SuggestionViewModel(randomTitle, randomMeal, randomUrl, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}