package vaida.dryzaite.foodmood.ui.addRecipe

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentAddRecipeBinding
import vaida.dryzaite.foodmood.ui.main.MainActivity
import vaida.dryzaite.foodmood.utilities.isValidUrl
import javax.inject.Inject


class AddRecipeFragment : Fragment(){

    @Inject lateinit var viewModel: AddRecipeViewModel
    private lateinit var binding: FragmentAddRecipeBinding

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_add_recipe, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        observeMealTypeSelected()
        observeOnAddRecipe()

        return binding.root
    }


    //    since no click listener to save item, the observer send Success/error toast
    private fun observeOnAddRecipe() {
        viewModel.onSaveLiveData.observe(viewLifecycleOwner, Observer { saved ->
            saved?.let {
                if (saved) {
                    Toast.makeText(context, getString(R.string.saved_successfully), Toast.LENGTH_SHORT).show()
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
    }


    //observer that as item clicked changes the background of item
    private fun observeMealTypeSelected() {
        viewModel.onMealSelected.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                viewModel.mealTypeSelectionCompleted()
            }
        })

    }

}
