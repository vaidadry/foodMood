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
import vaida.dryzaite.foodmood.databinding.FragmentHomeBinding
import vaida.dryzaite.foodmood.model.room.RecipeDatabase


class HomeFragment : Fragment() {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application

        //accessing DB
        val dataSource = RecipeDatabase.getInstance(application).recipeDao

        //enabling viewModel data binding in fragment
        val viewModelFactory = HomeViewModelFactory(dataSource, application)
        homeViewModel = ViewModelProvider(this, viewModelFactory).get(HomeViewModel::class.java)
        binding.lifecycleOwner = this
        binding.homeViewModel = homeViewModel

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



//        setupButtonAction()
    }


    private fun getRandomRecipe(): Boolean {
        Timber.i("getRandomRecipe() called, not yet generating recipe")
        homeViewModel.getAllRecipesLiveData().observe(viewLifecycleOwner, Observer { recipes ->
            recipes?.let {
                Timber.i("getRandomRecipe() observer used to get to viewModel")
                homeViewModel.getRandomRecipe(it)
                Timber.i("got back to fragment")
            }
        })
        return !homeViewModel.randomRecipe.value?.title.isNullOrEmpty()

    }

    private fun setupButtonAction() {
        binding.randomButton.setOnClickListener {
            Timber.i("before if statement")
            if (getRandomRecipe()) {
                val randomTitle = homeViewModel.randomRecipe.value?.title.toString()
                val randomMeal = homeViewModel.randomRecipe.value?.meal!!
                val randomUrl = homeViewModel.randomRecipe.value?.recipe.toString()

                findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToSuggestionFragment(
                        randomTitle,
                        randomMeal,
                        randomUrl
                    )
                )
                Timber.i("SHOWING generated recipe: $randomMeal, $randomTitle, $randomUrl")
            } else {
                Toast.makeText(
                    context,
                    getString(R.string.error_showing_recipe),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }



}