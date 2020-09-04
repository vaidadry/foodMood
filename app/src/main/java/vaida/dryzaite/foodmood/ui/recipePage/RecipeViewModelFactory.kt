package vaida.dryzaite.foodmood.ui.recipePage

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

//class RecipeViewModelFactory(
//    private val randomId: String,
//    private val application: Application): ViewModelProvider.Factory {
//    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(RecipeViewModel::class.java)) {
//            return RecipeViewModel(randomId, application) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class")
//    }
//
//}