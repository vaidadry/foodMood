package vaida.dryzaite.foodmood.ui.homePage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_suggestion.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.ui.main.MainActivity
import vaida.dryzaite.foodmood.viewmodel.SuggestionViewModel

class SuggestionFragment : Fragment() {

    private lateinit var viewModel: SuggestionViewModel

    companion object {
        const val TAG = "RANDOM_MEAL"
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_suggestion, container, false)
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        (activity as MainActivity).hideBottomNavigation()
//    }
//
//    override fun onDetach() {
//        (activity as MainActivity).showBottomNavigation()
//        super.onDetach()
//    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(SuggestionViewModel::class.java)


////        generateRandomEntry()
//        viewModel.generateRandomRecipe()
//        suggestionTextView.text =  viewModel.title
//        val selectedMealType = viewModel.meal
//        val meal = getString(R.string.suggestion_for_meal_text, selectedMealType)
//        suggestionForMealTextView.text = meal
//        val url = viewModel.recipe
//
//        Log.i(TAG, "generated entry title: ${viewModel.title}, meal: ${viewModel.meal}, url: ${viewModel.recipe} ")


//        viewModel.generateRandomRecipe()


        viewModel.getAllRecipesLiveData().observe(viewLifecycleOwner, Observer { recipes ->
            recipes?.let {
                if (recipes.isNotEmpty()) {
                    val recipe = recipes.random()
                    Log.i(TAG, "generated entry: $recipe")


                    setupViews(recipe)
                } else {
                    Toast.makeText(context, getString(R.string.error_showing_recipe), Toast.LENGTH_LONG).show()
                }
            }
        })

//        showMeHowButton.setOnClickListener {
//            redirectToRecipeUrl(url)
//        }
    }

    fun setupViews(recipe: RecipeEntry) {
        suggestionTextView.text =  recipe.title
        val selectedMealType = recipe.meal
        val meal = getString(R.string.suggestion_for_meal_text, selectedMealType)
        suggestionForMealTextView.text = meal
        val url = recipe.recipe

        Log.i(TAG, "generated entry title: ${recipe.title}, meal: ${recipe.meal}, url: ${recipe.recipe} ")
    }


    private fun redirectToRecipeUrl(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}
