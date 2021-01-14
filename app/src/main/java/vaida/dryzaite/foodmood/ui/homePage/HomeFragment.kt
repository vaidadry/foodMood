package vaida.dryzaite.foodmood.ui.homePage

import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentHome2Binding
import vaida.dryzaite.foodmood.ui.BaseFragment
import vaida.dryzaite.foodmood.ui.NavigationSettings

@AndroidEntryPoint
class HomeFragment : BaseFragment<HomeViewModel, FragmentHome2Binding>() {
    override val navigationSettings: NavigationSettings? = null
    override val layoutId: Int = R.layout.fragment_home_2

    // handling back button clicks not to return to recipe list
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragment2Self())
            }
        })
    }

    override fun getViewModelClass(): Class<HomeViewModel> {
        return HomeViewModel::class.java
    }
    override fun setupUI() {
        setupObservers()
    }

    private fun setupObservers() {
        //observing filtering
        viewModel.filteredRecipes.observe(viewLifecycleOwner, {})

        navigateToSuggestionPage()
    }

    private fun navigateToSuggestionPage() {
        viewModel.navigateToSuggestionPage.observe(viewLifecycleOwner, {
            when (it) {
                true -> {
                    this.findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragment2ToSuggestionFragment(viewModel.randomRecipe.value)
                    )
                    viewModel.doneNavigating()
                }
                false -> {
                    Toast.makeText(context, getString(R.string.error_showing_recipe), Toast.LENGTH_SHORT).show()
                    viewModel.doneNavigating()
                }
            }
        })
    }
}