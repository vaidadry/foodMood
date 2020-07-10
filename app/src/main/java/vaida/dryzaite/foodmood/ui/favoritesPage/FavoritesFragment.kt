package vaida.dryzaite.foodmood.ui.favoritesPage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentFavoritesBinding
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.ui.recipeList.DividerItemDecoration
import vaida.dryzaite.foodmood.ui.recipeList.RecipeListAdapter
import vaida.dryzaite.foodmood.ui.recipeList.RecipeListOnClickListener


class FavoritesFragment : Fragment(), RecipeListAdapter.RecipeListAdapterListener {

    private lateinit var favoritesViewModel: FavoritesViewModel
    private lateinit var binding: FragmentFavoritesBinding
    private lateinit var adapter: RecipeListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentFavoritesBinding.inflate(inflater, container, false)

        //reference to context, to get database instance
        val application = requireNotNull(this.activity).application

        val viewModelFactory = FavoritesViewModelFactory(application)
        favoritesViewModel = ViewModelProvider(this, viewModelFactory).get(FavoritesViewModel::class.java)

        binding.lifecycleOwner = this
        binding.favoritesViewModel = favoritesViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAdapter()

        setupRecyclerView()

        //updating Live data observer with ViewModel data
        favoritesViewModel.getFavorites().observe(viewLifecycleOwner, Observer { recipes ->
            recipes?.let {
                adapter.updateRecipes(recipes)
            }
            checkForEmptyState()
        })
        addListDividerDecoration()


        //set up observer to react on item taps and enable navigation
        favoritesViewModel.navigateToRecipeDetail.observe(viewLifecycleOwner, Observer { keyId->
            keyId?.let {
                this.findNavController().navigate(
                    FavoritesFragmentDirections.actionFavoritesFragmentToRecipeFragment(keyId))
                favoritesViewModel.onRecipeDetailNavigated()
            }
        })
    }


    private fun addListDividerDecoration() {
        //adding list divider decorations
        val heightInPixels = resources.getDimensionPixelSize(R.dimen.list_item_divider_height)
        binding.favoritesRecyclerview.addItemDecoration(
            DividerItemDecoration(
                R.color.Text,
                heightInPixels
            )
        )
    }


    private fun setupAdapter() {
        adapter = RecipeListAdapter(mutableListOf(), this, RecipeListOnClickListener { id ->
            favoritesViewModel.onRecipeClicked(id)
        })
    }

    private fun setupRecyclerView() {
        //setting up a Recyclerview
        val layoutManager = LinearLayoutManager(context)
        binding.favoritesRecyclerview.layoutManager = layoutManager
        binding.favoritesRecyclerview.adapter = adapter
    }

    //if no items, empty state text is shown
    private fun checkForEmptyState() {
        binding.emptyState.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.INVISIBLE
    }

//nenaudojam
    override fun deleteRecipeAtPosition(recipe: RecipeEntry) {
        TODO("Not yet implemented")
    }


}