package vaida.dryzaite.foodmood.ui.homePage

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import timber.log.Timber
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentHome2Binding
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.ui.main.MainActivity
import vaida.dryzaite.foodmood.ui.recipeList.RecipeListFragmentDirections
import javax.inject.Inject


class HomeFragment : Fragment() {

    @Inject lateinit var viewModel: HomeViewModel
    private lateinit var binding: FragmentHome2Binding

//    handling back button clicks not to return to recipe list
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragment2Self())
            }
        })
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home_2, container, false)


        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //observing filtering
        viewModel.filteredRecipes.observe(viewLifecycleOwner, {})

        navigateToSuggestionPage()
    }



    private fun navigateToSuggestionPage() {
        viewModel.navigateToSuggestionPage.observe(viewLifecycleOwner, {
            when (it) {
                true -> {
                    Timber.i("navigateToSuggestionPage(): SHOWING generated recipe: ${viewModel.randomRecipe.value}")
                    this.findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragment2ToSuggestionFragment(viewModel.randomRecipe.value)
                    )
                    viewModel.doneNavigating()
                }
                false -> {
                    Timber.i("Cant show recipe coz, no recipes added")
                    Toast.makeText(context, getString(R.string.error_showing_recipe), Toast.LENGTH_SHORT).show()
                    viewModel.doneNavigating()
                }
            }
        })
    }
}