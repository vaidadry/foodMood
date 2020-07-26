package vaida.dryzaite.foodmood.ui.discoverRecipes

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_discover_recipes.*
import timber.log.Timber
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentDiscoverRecipesBinding
import vaida.dryzaite.foodmood.ui.favoritesPage.SpacingItemDecorator


class DiscoverRecipesFragment : Fragment() {

    private lateinit var gridItemDecoration: RecyclerView.ItemDecoration
    private lateinit var adapter: DiscoverRecipesAdapter
    private lateinit var binding: FragmentDiscoverRecipesBinding

    private val viewModel: DiscoverRecipesViewModel by lazy {
        val viewModelFactory = DiscoverRecipesViewModelFactory()
        ViewModelProvider(this, viewModelFactory).get(DiscoverRecipesViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentDiscoverRecipesBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = DiscoverRecipesAdapter(mutableListOf(), DiscoverRecipesAdapter.OnClickListener {
            viewModel.displayRecipeDetails(it)
        })

        binding.discoverListRecyclerview.adapter = adapter

        // observing navigation state and navigating to detail page
        viewModel.navigateToSelectedRecipe.observe(viewLifecycleOwner, Observer {
            if ( null != it) {
                this.findNavController().navigate(
                    DiscoverRecipesFragmentDirections.actionDiscoverRecipesFragmentToDiscoverRecipeDetailFragment(it))
                viewModel.displayRecipeDetailsComplete()
            }
        })

        return binding.root
    }

    // loading initial values from API (no filter)
    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel.getExternalRecipes("")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        setupItemDecoration()
        setSearchInputListener()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_nav_menu, menu)

        //setup clicks on icons in toolbar
        val searchIcon = menu.findItem(R.id.search_menu_item)
        searchIcon.actionView.setOnClickListener {
            menu.performIdentifierAction(
                searchIcon.itemId,
                0
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Timber.i("onOptionsItemSelected")
        when (item.itemId) {
            R.id.search_menu_item -> {
                Timber.i("search selected")
                hideShowSearchBar()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    //    adding spacing decorator for equal spacing between grid items
    private fun setupItemDecoration() {
        val spacingInPixels = resources.getDimensionPixelSize(R.dimen.recipe_card_grid_layout_margin)
        gridItemDecoration = SpacingItemDecorator(3, spacingInPixels)
        binding.discoverListRecyclerview.addItemDecoration(gridItemDecoration)
    }

    private fun hideShowSearchBar() {
        binding.discoverSearchInput.visibility = if (discover_search_input.visibility == View.GONE) View.VISIBLE else View.GONE
    }


    private fun setSearchInputListener() {
        binding.discoverSearchInput.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                Timber.i("onQueryTextSubmit executed, query - $query")
                viewModel.getFilterResults(query)
                Timber.i("getFilterResults #1 executed query - $query")
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                Timber.i("adapter.filter accesed new text - $newText")
                viewModel.getFilterResults(newText)
                Timber.i("getFilterResults #2 executed; new text - $newText")
                return false
            }
        })
    }
}