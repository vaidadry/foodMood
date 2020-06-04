package vaida.dryzaite.foodmood.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Spinner
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.fragment_add_recipe.*

import vaida.dryzaite.foodmood.R

class AddRecipeFragment : Fragment() {

    private val INPUT_TAG = "INPUT_TAG"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_recipe, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val titleEt = findViewById<TextInputEditText>(R.id.titleInput)
//        val urlEt = findViewById<TextInputEditText>(R.id.urlInput)
//        val mealTypeSelectionEt = findViewById<Spinner>(R.id.spinnerMealTypeSelection)
////        val fishEt = findViewById<CheckBox>(R.id.fishCheckbox)
////        val comfortFoodEt = findViewById<CheckBox>(R.id.comfortFoodCheckbox)
//        val submitRecipeBtn = findViewById<Button>(R.id.submitRecipeBtn)


        submitRecipeBtn.setOnClickListener {
            // get text from input data
            val title = titleInput.text.toString()
            val url = urlInput.text.toString()
            val mealTypeSelection = spinnerMealTypeSelection.toString() ///čia blogai
            val fish = fishCheckbox
            val comfortFood = comfortFoodCheckbox
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
