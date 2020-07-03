package vaida.dryzaite.foodmood.ui.homePage

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import timber.log.Timber
//import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.RecipeRepository
import vaida.dryzaite.foodmood.model.room.RecipeDao

class HomeViewModel(val dataSource: RecipeDao, application: Application): AndroidViewModel(application) {
//    private val repository: RecipeRepository = Injection.provideRecipeRepository()

//    private val allRecipesLiveData = repository.getAllRecipes()

//    fun getAllRecipesLiveData() = allRecipesLiveData

    val database = dataSource
    private val allRecipesLiveData = database.getAllRecipes()
    fun getAllRecipesLiveData() = allRecipesLiveData



    // "_" means that it is backing property; in fragment only original ones must be used.

    private val _randomRecipe = MutableLiveData<RecipeEntry>()
    val randomRecipe: LiveData<RecipeEntry>
        get() = _randomRecipe


//    private var _eventEmptyList = MutableLiveData<Boolean> ()
//    val eventEmptyList: LiveData<Boolean>
//        get() = _eventEmptyList


    fun getRandomRecipe(recipeList: List<RecipeEntry>){
        if (!recipeList.isNullOrEmpty()) {
            _randomRecipe.value = recipeList.random()
            Timber.i("randomly generated entry in viewModel: ${randomRecipe.value}")
        }
    }

//     fun onEmptyList() {
//         _eventEmptyList.value = true
//     }
//



}