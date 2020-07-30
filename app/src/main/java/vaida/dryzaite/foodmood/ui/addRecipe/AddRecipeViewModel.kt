package vaida.dryzaite.foodmood.ui.addRecipe

import android.app.Application
import androidx.databinding.ObservableField
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import timber.log.Timber
import vaida.dryzaite.foodmood.app.Injection
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.RecipeGenerator
import vaida.dryzaite.foodmood.utilities.isValidUrl


class AddRecipeViewModel(private val generator: RecipeGenerator = RecipeGenerator(),
                         application: Application)
    : AndroidViewModel(application) {

    private val repository = Injection.provideRecipeRepository(application)

    private val newRecipe = MutableLiveData<RecipeEntry?>()


    //defining RecipeEntry  parameters
    val title = ObservableField<String>("")
    var veggie = ObservableField<Boolean>(false)
    var fish = ObservableField<Boolean>(false)
    var meal = ObservableField<Int>(0)
    var recipe = ObservableField<String>("")
    val ingredients = ""


    private lateinit var entry: RecipeEntry


    fun updateEntry() {
        entry = generator.generateRecipe(
            title.get() ?: "",
            veggie.get() ?: false,
            fish.get() ?: false,
            meal.get() ?: 0,
            recipe.get() ?: "",
            ingredients
        )
        newRecipe.value = entry
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
            repository.insertRecipe(entry)
            _onSaveLiveData.value = true
        } else {
            _onSaveLiveData.value = false
        }
    }

    fun onSaveLiveDataCompleted() {
        _onSaveLiveData.value = null
    }


}

