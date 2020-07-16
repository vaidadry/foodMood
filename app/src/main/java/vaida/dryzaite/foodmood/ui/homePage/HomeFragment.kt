package vaida.dryzaite.foodmood.ui.homePage

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
import vaida.dryzaite.foodmood.databinding.FragmentHome2Binding


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHome2Binding
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHome2Binding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        //enabling viewModel data binding in fragment
        val viewModelFactory = HomeViewModelFactory(application)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //blogai! turi but viewmodelyje, init block neveikia, gal kažką kito?
        homeViewModel.getAllRecipes().observe(viewLifecycleOwner, Observer {})


        observeMealSelectionStatus()
        navigateToSuggestionPage()
    }

    //observer of meal selection click event
    private fun observeMealSelectionStatus() {
        homeViewModel.onMealSelected.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                homeViewModel.mealSelectionCompleted()
            }
        })
    }

    private fun navigateToSuggestionPage() {
        homeViewModel.navigateToSuggestionPage.observe(viewLifecycleOwner, Observer {
            when (it) {
                true -> {
                    Timber.i("SHOWING generated recipe: ${homeViewModel.randomRecipe.value}")
                    this.findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragment2ToSuggestionFragment(homeViewModel.randomRecipe.value?.id.toString())
                    )
                    homeViewModel.doneNavigating()
                }
                false -> {
                    Timber.i("Cant show recipe coz, no recipes added")
                    Toast.makeText(
                        context,
                        getString(R.string.error_showing_recipe),
                        Toast.LENGTH_SHORT
                    ).show()
                    homeViewModel.doneNavigating()
                }
            }
        })
    }

}