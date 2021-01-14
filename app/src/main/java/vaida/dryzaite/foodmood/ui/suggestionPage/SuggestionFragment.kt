package vaida.dryzaite.foodmood.ui.suggestionPage

import android.content.Intent
import android.net.Uri
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentSuggestionBinding
import vaida.dryzaite.foodmood.ui.BaseFragment
import vaida.dryzaite.foodmood.ui.NavigationSettings

@AndroidEntryPoint
class SuggestionFragment : BaseFragment<SuggestionViewModel, FragmentSuggestionBinding>(){
    override val navigationSettings: NavigationSettings? = null
    override val layoutId: Int = R.layout.fragment_suggestion
    private val args by navArgs<SuggestionFragmentArgs>()

    override fun getViewModelClass(): Class<SuggestionViewModel> {
        return SuggestionViewModel::class.java
    }

    override fun setupUI() {
        viewModel.setRecipe(args.recipeEntry)
        binding.args = args

        setupObservers()
    }

    private fun setupObservers() {
        //observe button click
        viewModel.navigateToUrl.observe(viewLifecycleOwner, {
            it?.let {
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
