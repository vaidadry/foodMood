package vaida.dryzaite.foodmood.ui.suggestionPage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentSuggestionBinding

@AndroidEntryPoint
class SuggestionFragment : Fragment() {

    private lateinit var binding: FragmentSuggestionBinding
    private val viewModel: SuggestionViewModel by viewModels()
    private val args by navArgs<SuggestionFragmentArgs>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_suggestion, container, false)

        // enabling data binding between view Model and layout
        binding.viewModel = viewModel
        binding.lifecycleOwner = this

        //transferring args to VM
        viewModel.setRecipe(args.recipeEntry)
        binding.args = args

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //observer handling button SHOW ME HOW click
        viewModel.navigateToUrl.observe(viewLifecycleOwner, Observer {
            it?.let {
                Timber.i("$it is the URL to redirect")
               redirectToRecipeUrl(it)
                viewModel.onButtonClicked()
            }
        })
    }

    private fun redirectToRecipeUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        view?.context?.startActivity(intent)
    }

}
