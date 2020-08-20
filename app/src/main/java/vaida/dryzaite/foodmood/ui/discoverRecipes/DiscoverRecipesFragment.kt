package vaida.dryzaite.foodmood.ui.discoverRecipes

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_discover_recipes.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import timber.log.Timber
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentDiscoverRecipesBinding
import vaida.dryzaite.foodmood.ui.favoritesPage.SpacingItemDecorator

@ExperimentalCoroutinesApi
class DiscoverRecipesFragment : Fragment() {

    private lateinit var gridItemDecoration: RecyclerView.ItemDecoration
    private lateinit var adapter: DiscoverRecipesAdapter
    private lateinit var binding: FragmentDiscoverRecipesBinding

    private val viewModel: DiscoverRecipesViewModel by lazy {
        val application = requireNotNull(this.activity).application
        val viewModelFactory = DiscoverRecipesViewModelFactory(application)
        ViewModelProvider(this, viewModelFactory).get(DiscoverRecipesViewModel::class.java)
    }

    private var searchJob: Job? = null

    @InternalCoroutinesApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment
        binding = FragmentDiscoverRecipesBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        adapter = DiscoverRecipesAdapter(DiscoverRecipesAdapter.OnClickListener {
            viewModel.displayRecipeDetails(it)
        })

        binding.discoverListRecyclerview.adapter = adapter

        //preparing search functionality
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_QUERY
        search(query)
        initSearch(query)


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

    // to avoid  re-loading with state change
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Timber.i("onSaveInstanceState initiated")
        outState.putString(LAST_SEARCH_QUERY, binding.searchRecipe.text.trim().toString())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        setHasOptionsMenu(true)

        setupItemDecoration()
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

    // to start search job + update adapter
    private fun search(searchQuery: String) {
        searchJob?.cancel()
        Timber.i("search method initiated $searchQuery")
        searchJob = lifecycleScope.launch {
            Timber.i("search job started")
            viewModel.searchExternalRecipes(searchQuery).collectLatest {
                Timber.i("paging data returned : $it")
                adapter.submitData(it)
            }
            Timber.i("search job finished")
        }
    }


    private fun updateRecipeListFromInput() {
        Timber.i("updateRecipeListFromInput() initiated")
        binding.searchRecipe.text.trim().let {
            if (it.isNotEmpty()) {
                Timber.i("updateRecipeListFromInput() : ${binding.searchRecipe.text} is searched")
                if (it.isNotEmpty()) {
                    search(it.toString())
                }
            }
        }
    }

    // triggering search by setting up listeners
    @InternalCoroutinesApi
    private fun initSearch(query: String) {
        binding.searchRecipe.setText(query)
        Timber.i("initSearch initiated, query - $query")

        binding.searchRecipe.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                updateRecipeListFromInput()
                true
            } else {
                false
            }
        }
        binding.searchRecipe.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                updateRecipeListFromInput()
                true
            } else {
                false
            }
        }

//        // Scroll to top when the list is refreshed from network.
        lifecycleScope.launch {
            adapter.loadStateFlow
                // Only emit when REFRESH LoadState for RemoteMediator changes.
                .distinctUntilChangedBy { it.refresh }
                // Only react to cases where Remote REFRESH completes i.e., NotLoading.
                .filter { it.refresh is LoadState.NotLoading }
                .collect { binding.discoverListRecyclerview.scrollToPosition(0) }
        }
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

    companion object {
        private const val LAST_SEARCH_QUERY: String = "last_search_query"
        private const val DEFAULT_QUERY = ""
    }

}