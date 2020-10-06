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

class AddRecipeViewModel2 @ViewModelInject constructor(
    private val generator: RecipeGenerator,
    private val repository: RecipeRepository) : ViewModel() {


    private val newRecipeForDb = MutableLiveData<RecipeEntry?>()


    private fun insertRecipe(recipeEntry: RecipeEntry) = viewModelScope.launch(Dispatchers.IO) {
        repository.insertRecipe(recipeEntry)
    }

    //defining RecipeEntry  parameters
    val title = ObservableField<String?>("")
    var veggie = ObservableField<Boolean>(false)
    var fish = ObservableField<Boolean>(false)
    var meal = ObservableField<Int>(0)
    var recipe = ObservableField<String>("")
    var ingredients = ObservableField<String>("")


    private lateinit var entry: RecipeEntry


    private fun updateEntry() {
        entry = generator.generateRecipe(
            title.get() ?: "",
            veggie.get() ?: false,
            fish.get() ?: false,
            meal.get() ?: 0,
            recipe.get() ?: "",
            ingredients.get() ?: ""
        )
        newRecipeForDb.value = entry
    }

    //parameter to observe state when meal is selected
    private val _onMealSelected = MutableLiveData<Boolean?>()
    val onMealSelected: LiveData<Boolean?>
        get() = _onMealSelected


    fun onSetMealType(mealSelection: Int) {
        this.meal.set(mealSelection)
        _onMealSelected.value = true
    }

    fun mealTypeSelectionCompleted() {
        _onMealSelected.value = null
    }


    // form validation - valid url and non empty fields
    private fun canSaveRecipe(): Boolean {
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

    //adding custom property with getter, for dataBinding for state
    private val _onSaveLiveData = MutableLiveData<Boolean?>()
    val onSaveLiveData: LiveData<Boolean?>
        get() = _onSaveLiveData


    //method's added with data binding, updates with user input, gets validation,
    // if passes -  is inserted to DB off main thread with coroutines
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

