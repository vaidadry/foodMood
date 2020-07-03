package vaida.dryzaite.foodmood.ui.suggestionPage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import vaida.dryzaite.foodmood.databinding.FragmentSuggestionBinding
import vaida.dryzaite.foodmood.model.room.RecipeDatabase

class SuggestionFragment : Fragment() {

    private lateinit var binding: FragmentSuggestionBinding
    private lateinit var suggestionViewModel: SuggestionViewModel
    private lateinit var viewModelFactory: SuggestionViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSuggestionBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val dataSource = RecipeDatabase.getInstance(application).recipeDao

        //can code be cleaner here? Yes, need to pass only ID and then get all info from repo ---> LATER
        viewModelFactory = SuggestionViewModelFactory(
            SuggestionFragmentArgs.fromBundle(requireArguments()).randomTitle,
            SuggestionFragmentArgs.fromBundle(requireArguments()).randomMeal,
            SuggestionFragmentArgs.fromBundle(requireArguments()).randomUrl,
            dataSource)
        suggestionViewModel = ViewModelProvider(this, viewModelFactory).get(SuggestionViewModel::class.java)

//        enabling data binding between view Model and layout
        binding.suggestionViewModel = suggestionViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.showMeHowButton.setOnClickListener {
            redirectToRecipeUrl(suggestionViewModel.url)
        }
    }

    private fun redirectToRecipeUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        view?.context?.startActivity(intent)
    }
}
