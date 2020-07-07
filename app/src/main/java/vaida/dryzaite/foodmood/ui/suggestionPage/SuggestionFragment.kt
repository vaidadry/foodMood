package vaida.dryzaite.foodmood.ui.suggestionPage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import timber.log.Timber
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
        val arguments = SuggestionFragmentArgs.fromBundle(requireArguments())

        viewModelFactory = SuggestionViewModelFactory(arguments.randomId, application)
        suggestionViewModel = ViewModelProvider(this, viewModelFactory).get(SuggestionViewModel::class.java)

//        enabling data binding between view Model and layout
        binding.suggestionViewModel = suggestionViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //observer handling button SHOW ME HOW click
        suggestionViewModel.navigateToUrl.observe(viewLifecycleOwner, Observer {
            it?.let {
                Timber.i("$it is the URL to redirect")
               redirectToRecipeUrl(it)
                suggestionViewModel.onButtonClicked()
            }
        })
    }

    private fun redirectToRecipeUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        view?.context?.startActivity(intent)
    }

}
