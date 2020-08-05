package vaida.dryzaite.foodmood.ui.addRecipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import timber.log.Timber
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentAddRecipe2Binding
import vaida.dryzaite.foodmood.model.RecipeGenerator
import vaida.dryzaite.foodmood.utilities.isValidUrl

class AddRecipeFragment2 : Fragment(){

    private lateinit var viewModel: AddRecipeViewModel2
    private lateinit var binding: FragmentAddRecipe2Binding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentAddRecipe2Binding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        val externalRecipe = AddRecipeFragment2Args.fromBundle(requireArguments()).selectedRecipe

        val viewModelFactory = AddRecipeViewModelFactory2(RecipeGenerator(), application, externalRecipe)
        viewModel = ViewModelProvider(this, viewModelFactory).get(AddRecipeViewModel2::class.java)
        viewModel.title.set(externalRecipe.title)
        viewModel.recipe.set(externalRecipe.url)

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
                    findNavController().navigate(R.id.action_addRecipeFragment2_to_recipeListFragment)
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