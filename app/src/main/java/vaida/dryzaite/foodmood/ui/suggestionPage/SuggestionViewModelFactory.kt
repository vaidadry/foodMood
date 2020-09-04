package vaida.dryzaite.foodmood.ui.suggestionPage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException


//can be used if there are multiple viewModels (?)
//class SuggestionViewModelFactory(private val randomId: String,
//                                 private val application: Application): ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(SuggestionViewModel::class.java)) {
//            return SuggestionViewModel(randomId, application) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//
//}