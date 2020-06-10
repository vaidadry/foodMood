package vaida.dryzaite.foodmood.ui.recipeList

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import kotlinx.android.synthetic.main.fragment_add_recipe.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.ui.main.MainActivity
import vaida.dryzaite.foodmood.viewmodel.AddRecipeViewModel


class AddRecipeFragment : Fragment(){

    private lateinit var viewModel: AddRecipeViewModel

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

        viewModel = ViewModelProvider(this).get(AddRecipeViewModel::class.java)

        configureEditText()
        configureSpinner()
        configureSpinnerListener()
        configureSaveButton()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        (activity as MainActivity).showBottomNavigation()
        super.onDetach()
    }

    override fun onResume() {
        super.onResume()
        (activity as MainActivity).hideBottomNavigation()
    }


//    HELPERS for adding input data to ViewModel

    //auto-suggestion-syntax
    private fun configureSpinner() {
        val mealList = R.array.meals
        val adapter = activity?.applicationContext?.let {
            ArrayAdapter.createFromResource(
                it, mealList, android.R.layout.simple_spinner_item)
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerMealTypeSelection.adapter = adapter
    }

    private fun configureSpinnerListener() {
        spinnerMealTypeSelection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                Toast.makeText(context, getString(R.string.please_select_meal_error), Toast.LENGTH_SHORT).show()
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long
            ) {
                viewModel.mealTypeSelected(position)
            }
        }
    }

    private fun configureEditText() {
        titleInput.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.title = s.toString()
            }
        })
        urlInput.addTextChangedListener( object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {}
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.recipe = s.toString()
            }
        })
    }

    private fun configureSaveButton() {
        submitRecipeBtn.setOnClickListener {
            viewModel.fish = fishCheckbox.isChecked
            viewModel.comfortFood = comfortFoodCheckbox.isChecked
            Log.i(INPUT_TAG, "data collected: title: ${viewModel.title}, url: ${viewModel.recipe}, fish: ${viewModel.fish}, comfortFood: ${viewModel.comfortFood}")

            if (viewModel.saveNewRecipe()) {
                Toast.makeText(context, getString(R.string.saved_successfully), Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addRecipeFragment_to_recipeListFragment)
            } else {
                Toast.makeText(context, getString(R.string.error_saving_recipe), Toast.LENGTH_SHORT).show()
            }
        }
    }

}
