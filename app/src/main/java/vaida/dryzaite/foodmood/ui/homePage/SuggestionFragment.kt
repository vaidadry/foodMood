package vaida.dryzaite.foodmood.ui.homePage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.fragment_suggestion.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.model.RecipeBook
import vaida.dryzaite.foodmood.ui.main.MainActivity

class SuggestionFragment : Fragment() {

    var id: Int? = 0
    private var meal = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_suggestion, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        nav_view.visibility = View.INVISIBLE   -- kaip daryti kad nesulūžtų??

        val (suggestionTitle, selectedMealType, url) = receiveIntent()

        suggestionTextView.text = suggestionTitle

        meal = getString(R.string.suggestion_for_meal_text, selectedMealType)
        suggestionForMealTextView.text = meal

        showMeHowButton.setOnClickListener {
            redirectToRecipeUrl(url)
        }
    }

    // converting random entry data to text fields for activity
    private fun receiveIntent(): Triple<String?, String?, String?> {
        id = arguments?.getInt("random_id")

        val entry = id?.let { RecipeBook.getRecipeEntryById(it) }
        val suggestionTitle = entry?.title
        val selectedMealType = entry?.meal
        val url = entry?.recipe
        return Triple(suggestionTitle, selectedMealType, url)
    }

    private fun redirectToRecipeUrl(url: String?) {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(url)
        startActivity(intent)
    }
}
