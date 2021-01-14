package vaida.dryzaite.foodmood.ui.addRecipe

import androidx.databinding.ObservableField
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.RecipeGenerator
import vaida.dryzaite.foodmood.repository.RecipeRepository
import vaida.dryzaite.foodmood.utilities.isValidUrl

class AddRecipeViewModel @ViewModelInject constructor(
    private val generator: RecipeGenerator,
    private val repository: RecipeRepository) : ViewModel() {

    private val newRecipe = MutableLiveData<RecipeEntry?>()
    lateinit var entry: RecipeEntry

    var title = ObservableField<String?>()
    var veggie = ObservableField<Boolean>()
    var fish = ObservableField<Boolean>()
    var meal = ObservableField<Int>()
    var recipe = ObservableField<String>()
    var ingredients = ObservableField<String>()

    // meal selected state
    private val _onMealSelected = MutableLiveData<Boolean?>()
    val onMealSelected: LiveData<Boolean?> = _onMealSelected

    // save state
    private val _onSaveLiveData = MutableLiveData<Boolean?>()
    val onSaveLiveData: LiveData<Boolean?> = _onSaveLiveData

    private fun insertRecipe(recipeEntry: RecipeEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertRecipe(recipeEntry)
    }

    private fun updateEntry() {
        entry = generator.generateRecipe(
            title.get() ?: "",
            veggie.get() ?: false,
            fish.get() ?: false,
            meal.get() ?: 0,
            recipe.get() ?: "",
            ingredients.get() ?: ""
        )
        newRecipe.value = entry
    }

    fun onSetMealType(mealSelection: Int) {
        this.meal.set(mealSelection)
        _onMealSelected.value = true
    }

    fun mealTypeSelectionCompleted() {
        _onMealSelected.value = null
    }

    // form validation - url and empty fields
    fun canSaveRecipe(): Boolean {
        val title = this.title.get()
        val recipe = this.recipe.get()
        title?.let {
            if (recipe != null) {
                if (!recipe.isValidUrl()) {
                    return false
                }
                return title.isNotEmpty() && recipe.isNotEmpty() && meal.get() != 0
            }
        }
        return false
    }

    fun saveNewRecipe() {
        updateEntry()
        return if (canSaveRecipe()) {
            Timber.i("added: $entry")
            insertRecipe(entry)
            _onSaveLiveData.value = true
        } else {
            _onSaveLiveData.value = false
        }
    }

    fun onSaveLiveDataCompleted() {
        _onSaveLiveData.value = null
    }

}

