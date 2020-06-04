package vaida.dryzaite.foodmood.ui.homePage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_home.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.model.RecipeBook


class HomeFragment : Fragment() {
    private var suggestionId = 0
    private lateinit var comm: Communicator

    companion object {
        const val TAG = "RANDOM_MEAL"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        comm = activity as Communicator
        random_Button.setOnClickListener { view ->
            generateRandomEntry()
            passDataToFragment()
        }
    }


    private fun generateRandomEntry() {
        val recipeList = RecipeBook.getRecipes()
        if (recipeList.isNotEmpty()) {
            val recipeEntry = recipeList.random()
            suggestionId = recipeEntry.id
            Log.v(TAG, "meal generated: $suggestionId")
        } else {
            Toast.makeText(context, getString(R.string.no_recipe_available_toast), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun passDataToFragment() {
        if (suggestionId != 0) {
            comm.passData(suggestionId)
        }
    }
}