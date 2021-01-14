package vaida.dryzaite.foodmood.ui.addRecipe

import android.widget.Toast
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentAddRecipeBinding
import vaida.dryzaite.foodmood.ui.BaseFragment
import vaida.dryzaite.foodmood.ui.NavigationSettings
import vaida.dryzaite.foodmood.utilities.isValidUrl

@AndroidEntryPoint
class AddRecipeFragment : BaseFragment<AddRecipeViewModel, FragmentAddRecipeBinding>(){
    override val navigationSettings: NavigationSettings? = null
    override val layoutId: Int = R.layout.fragment_add_recipe

    override fun getViewModelClass(): Class<AddRecipeViewModel> {
        return AddRecipeViewModel::class.java
    }

    override fun setupUI() {
        setupObservers()
    }

    private fun setupObservers() {
        // informs if changes saved
        viewModel.onSaveLiveData.observe(viewLifecycleOwner, { saved ->
            saved?.let {
                if (saved) {
                    Toast.makeText(context, getString(R.string.saved_successfully), Toast.LENGTH_SHORT).show() // TODO- change to snackbars
                    findNavController().navigate(R.id.action_addRecipeFragment_to_recipeListFragment)
                } else {
                    if (!viewModel.recipe.get()?.isValidUrl()!!) {
                        Toast.makeText(context, getString(R.string.incorrect_url), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context, getString(R.string.error_saving_recipe_not_filled), Toast.LENGTH_SHORT).show()
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
