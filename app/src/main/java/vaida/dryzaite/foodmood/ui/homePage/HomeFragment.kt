package vaida.dryzaite.foodmood.ui.homePage

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import timber.log.Timber
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentHome2Binding
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.ui.recipeList.RecipeListFragmentDirections


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHome2Binding
    private lateinit var viewModel: HomeViewModel

    private var backPressedTaps = 0

//    handling back button clicks not to return to recipe list
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(HomeFragmentDirections.actionHomeFragment2Self())
            }
        })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHome2Binding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        //enabling viewModel data binding in fragment
        val viewModelFactory = HomeViewModelFactory(application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //blogai! turi but viewmodelyje, gal kažką kito?
//        viewModel.allRecipesLiveData.observe(viewLifecycleOwner, Observer {
//            Timber.i("${viewModel.allRecipesLiveData.value}")
//        })


//        observeMealSelection()
        navigateToSuggestionPage()
    }



//    private fun observeMealSelection() {
//        viewModel.meal.observe(viewLifecycleOwner, Observer {
//            if (it != null) {
//                viewModel.getFilteredRecipes(it)
//                Timber.i("getFilteredRecipes called ${viewModel.filteredRecipes.value}")
//            } else {
//                viewModel.getAllRecipes().value
//                Timber.i("get all recipes called ${viewModel.getAllRecipes().value}")
//            }
//        })
//    }


    // daryti gal on click listener paprasta ir ten paleisti observer?

    private fun navigateToSuggestionPage() {
        viewModel.navigateToSuggestionPage.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    Timber.i("SHOWING generated recipe: ${viewModel.randomRecipe.value}")
                    this.findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragment2ToSuggestionFragment(viewModel.randomRecipe.value?.id.toString())
                    )
                    viewModel.doneNavigating()
                }
                false -> {
                    Timber.i("Cant show recipe coz, no recipes added")
                    Toast.makeText(
                        context,
                        getString(R.string.error_showing_recipe),
                        Toast.LENGTH_SHORT
                    ).show()
                    viewModel.doneNavigating()
                }
            }
        })
    }
}