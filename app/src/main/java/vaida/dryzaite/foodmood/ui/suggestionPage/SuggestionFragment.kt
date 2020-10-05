package vaida.dryzaite.foodmood.ui.suggestionPage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import timber.log.Timber
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentSuggestionBinding
import vaida.dryzaite.foodmood.ui.main.MainActivity
import javax.inject.Inject

class SuggestionFragment : Fragment() {

    private lateinit var binding: FragmentSuggestionBinding
    @Inject lateinit var viewModel: SuggestionViewModel
    private val args by navArgs<SuggestionFragmentArgs>()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }
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
