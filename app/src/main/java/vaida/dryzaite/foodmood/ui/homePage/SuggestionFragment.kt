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
import kotlinx.android.synthetic.main.fragment_suggestion.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.ui.main.MainActivity

class SuggestionFragment : Fragment() {

    private var suggestionId = ""
    private var meal = ""

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

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).hideBottomNavigation()
    }

    override fun onDetach() {
        (activity as MainActivity).showBottomNavigation()
        super.onDetach()
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

////        generateRandomEntry()
//        val (suggestionTitle, selectedMealType, url) = collectData()
//        suggestionTextView.text = suggestionTitle
//        meal = getString(R.string.suggestion_for_meal_text, selectedMealType)
//        suggestionForMealTextView.text = meal
//

//        showMeHowButton.setOnClickListener {
//            redirectToRecipeUrl(url)
//        }
    }


//    // gather textview data for random entry
//    private fun collectData(): Triple<String?, String?, String?> {
//        val entry = suggestionId.let { RecipeStore.getRecipeEntryById(it) }
//        val suggestionTitle = entry?.title
//        val selectedMealType = entry?.meal
//        val url = entry?.recipe
//        return Triple(suggestionTitle, selectedMealType, url)
//    }
//
    private fun redirectToRecipeUrl(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}
