package vaida.dryzaite.foodmood.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.RecipeGenerator


class AddRecipeViewModel(private val generator: RecipeGenerator = RecipeGenerator()
//                         ,private val repository: RecipeRepository = RoomRepository() // changed to injection below - palikta jei reiktu atstatyti
     ) : ViewModel() {

    private val repository = Injection.provideRecipeRepository()

    private val recipeLiveData = MutableLiveData<RecipeEntry>()

    var title = ""
    var comfortFood = false
    var fish = false
    var meal = "dinner"
    var recipe = ""

    lateinit var entry: RecipeEntry

    fun updateEntry() {
        entry = generator.generateRecipe(title, comfortFood, fish, meal, recipe)
        recipeLiveData.postValue(entry)
    }

    fun mealTypeSelected(position: Int){
        val mealList = listOf<String>("Select-meal", "breakfast", "brunch", "lunch", "dinner", "sweets") //laikinai!!! maybe, enum classes reiks?
        meal = mealList[position]
        updateEntry()
    }

    fun canSaveRecipe(): Boolean {
         return title.isNotEmpty() && recipe.isNotEmpty() && meal != "Select-meal"
    }

    fun saveNewRecipe(): Boolean {
        return if (canSaveRecipe()) {
            repository.saveNewRecipe(entry)
            true
        } else {
            false
        }
    }

}
