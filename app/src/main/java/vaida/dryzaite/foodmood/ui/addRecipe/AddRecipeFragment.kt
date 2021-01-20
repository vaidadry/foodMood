package vaida.dryzaite.foodmood.ui.addRecipe

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentAddRecipeBinding
import vaida.dryzaite.foodmood.ui.BaseFragment
import vaida.dryzaite.foodmood.ui.NavigationSettings
import vaida.dryzaite.foodmood.utilities.isValidUrl

@AndroidEntryPoint
class AddRecipeFragment : BaseFragment<AddRecipeViewModel, FragmentAddRecipeBinding>() {
    override val navigationSettings: NavigationSettings? = null
    override val layoutId: Int = R.layout.fragment_add_recipe
    private val args by navArgs<AddRecipeFragmentArgs>()

    override fun getViewModelClass(): Class<AddRecipeViewModel> {
        return AddRecipeViewModel::class.java
    }

    override fun setupUI() {
        viewModel.title.set(args.selectedRecipe?.title ?: "")
        viewModel.href.set(args.selectedRecipe?.href ?: "")
        viewModel.ingredients.set(args.selectedRecipe?.ingredients ?: "")
        viewModel.thumbnail.set(args.selectedRecipe?.thumbnail)
        setupObservers()
    }

    private fun setupObservers() {
        // informs if changes saved
        viewModel.onSaveLiveData.observe(viewLifecycleOwner, { saved ->
            saved?.let {
                if (saved) {
                    Snackbar.make(binding.addRecipeFragment, getString(R.string.addRecipe_saveSuccess), Snackbar.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_addRecipeFragment_to_recipeListFragment)
                } else {
                    if (!viewModel.href.get()?.isValidUrl()!!) {
                        Snackbar.make(binding.addRecipeFragment, getString(R.string.addRecipe_error_incorrectUrl), Snackbar.LENGTH_SHORT).show()
                    } else {
                        Snackbar.make(binding.addRecipeFragment, getString(R.string.addRecipe_error_fieldsNotFilled), Snackbar.LENGTH_SHORT).show()
                    }
                }
                viewModel.onSaveLiveDataCompleted()
            }
        })

        viewModel.onMealSelected.observe(viewLifecycleOwner, {
            if (it == true) {
                viewModel.mealTypeSelectionCompleted()
            }
        })
    }
}
