package vaida.dryzaite.foodmood.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

import android.widget.Spinner
import com.google.android.material.textfield.TextInputEditText

import vaida.dryzaite.foodmood.R

class AddNewRecipeFormActivity : AppCompatActivity() {

    private val INPUT_TAG = "INPUT_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_new_recipe_form)

        val titleEt = findViewById<TextInputEditText>(R.id.titleInput)
        val urlEt = findViewById<TextInputEditText>(R.id.urlInput)
        val mealTypeSelectionEt = findViewById<Spinner>(R.id.spinnerMealTypeSelection)
//        val fishEt = findViewById<CheckBox>(R.id.fishCheckbox)
//        val comfortFoodEt = findViewById<CheckBox>(R.id.comfortFoodCheckbox)
        val submitRecipeBtn = findViewById<Button>(R.id.submitRecipeBtn)


        submitRecipeBtn.setOnClickListener {
            // get text from input data
            val title = titleEt.text.toString()
            val url = urlEt.text.toString()
            val mealTypeSelection = mealTypeSelectionEt.toString() ///čia blogai
//            val fish = fishEt.toBoolean
//            val comfortFood = comfortFoodEt.toBoolean
            Log.i(INPUT_TAG, "data collected: title: $title, url: $url, meal: $mealTypeSelection")
/*
            // collecting form data
            val intent = Intent(this, ThankYouActivity::class.java)
            intent.putExtra("Title", title)
            intent.putExtra("Url", url)
//            intent.putExtra("MealTypeSelection", mealTypeSelection)
//            intent.putExtra("Fish", fish)
//            intent.putExtra("comfortFood", comfortFood)
            startActivity(intent)
            */

        }
    }
}
