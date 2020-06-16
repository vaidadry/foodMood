package vaida.dryzaite.foodmood.viewmodel

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.RecipeGenerator
import vaida.dryzaite.foodmood.utilities.isValidUrl


class AddRecipeViewModel(private val generator: RecipeGenerator = RecipeGenerator()
//                         ,private val repository: RecipeRepository = RoomRepository() // changed to injection below - palikta jei reiktu atstatyti
     ) : ViewModel() {

    private val repository = Injection.provideRecipeRepository()
    private val recipeLiveData = MutableLiveData<RecipeEntry>()


    //adding custom property with getter func, for databinding to fragment
    private val saveLiveData = MutableLiveData<Boolean>()
    fun getSaveLiveData(): LiveData<Boolean> = saveLiveData


    var title = ObservableField<String>("")
    var veggie = ObservableField<Boolean>(false)
    var fish = ObservableField<Boolean>(false)
    var meal = ""
    var recipe = ObservableField<String>("")

    lateinit var entry: RecipeEntry

    fun updateEntry() {
        entry = generator.generateRecipe(title.get() ?: "", veggie.get() ?: false, fish.get() ?: false, meal, recipe.get() ?: "")
        recipeLiveData.postValue(entry)
    }

    fun mealTypeSelected(position: Int){
        val mealList = listOf<String>("Select-meal", "breakfast", "brunch", "lunch", "dinner", "sweets") //laikinai!!! maybe, enum classes reiks?
        meal = mealList[position]
        updateEntry()
    }

    fun canSaveRecipe(): Boolean {
        val title = this.title.get()
        val recipe = this.recipe.get()
        title?.let {
            if (recipe != null) {
                    return title.isNotEmpty() && recipe.isNotEmpty() && meal != "Select-meal"
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
            Log.i("Added", "title: $title, recipe: $recipe, veggie: $veggie, fish: $fish, meal: $meal")
            saveLiveData.postValue(true)
        } else {
            saveLiveData.postValue(false)
        }
    }

}
