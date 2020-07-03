package vaida.dryzaite.foodmood.ui.suggestionPage

import androidx.lifecycle.ViewModel
import timber.log.Timber
import vaida.dryzaite.foodmood.model.room.RecipeDao

class SuggestionViewModel(randomTitle: String, randomMeal: Int, randomUrl: String, dataSource: RecipeDao): ViewModel() {

    val database = dataSource
    var title = randomTitle
    var meal = randomMeal
    var url = randomUrl

    init {
        Timber.i("random recipe on screen: $title, $meal, $url")
    }

}