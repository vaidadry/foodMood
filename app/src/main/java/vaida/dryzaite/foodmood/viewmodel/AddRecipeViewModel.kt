package vaida.dryzaite.foodmood.viewmodel

import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.Toast
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.RecipeGenerator
import vaida.dryzaite.foodmood.ui.recipeList.AddRecipeFragment
import vaida.dryzaite.foodmood.utilities.isValidUrl
import java.util.*


class AddRecipeViewModel(private val generator: RecipeGenerator = RecipeGenerator()
//                         ,private val repository: RecipeRepository = RoomRepository() // changed to injection below - palikta jei reiktu atstatyti
     ) : ViewModel() {

    private val repository = Injection.provideRecipeRepository()
    private val recipeLiveData = MutableLiveData<RecipeEntry>()


    //adding custom property with getter func, for dataBinding to fragment
    private val saveLiveData = MutableLiveData<Boolean>()
    fun getSaveLiveData(): LiveData<Boolean> = saveLiveData


    var title = ObservableField<String>("")
    var veggie = ObservableField<Boolean>(false)
    var fish = ObservableField<Boolean>(false)
    var meal = ""
    var recipe = ObservableField<String>("")


    private lateinit var entry: RecipeEntry


    fun updateEntry() {
        entry = generator.generateRecipe(title.get() ?: "", veggie.get() ?: false, fish.get() ?: false, meal, recipe.get() ?: "")
        recipeLiveData.postValue(entry)
    }

    // tracks spinner choice. cant set up Observable field coz spinner is related to avatar pic choice.
    val clicksListener = object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}

        override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            meal = (parent?.getItemAtPosition(position) as String).toLowerCase(Locale.ROOT)
        }
    }


    ///unfinished - TOAST about invalid URL

    fun canSaveRecipe(): Boolean {
        val title = this.title.get()
        val recipe = this.recipe.get()
        title?.let {
            if (recipe != null) {
                if (!recipe.isValidUrl()) {
                    // TODO(show Toast on invalid URL)- singleLiveEvent?, RXJava? really?
                    return false
                    }
                return title.isNotEmpty() && recipe.isNotEmpty() && meal != "select meal"
                }
            }
        return false
    }


    //updated method (to work with data binding, therefore no need of button click listener)
    fun saveNewRecipe() {
        updateEntry()
        return if (canSaveRecipe()) {
            Log.i("added", "$entry")
            repository.saveNewRecipe(entry)
            saveLiveData.postValue(true)
        } else {
            saveLiveData.postValue(false)
        }
    }

}

