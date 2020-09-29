package vaida.dryzaite.foodmood.ui.discoverRecipes

import android.content.Context
import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
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
import vaida.dryzaite.foodmood.ui.main.MainActivity
import vaida.dryzaite.foodmood.utilities.DEFAULT_SEARCH_QUERY
import vaida.dryzaite.foodmood.utilities.DividerItemDecoration
import vaida.dryzaite.foodmood.utilities.LAST_SEARCH_QUERY
import javax.inject.Inject

@ExperimentalCoroutinesApi
class DiscoverRecipesFragment : Fragment() {

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var adapter: DiscoverRecipesAdapter
    private lateinit var binding: FragmentDiscoverRecipesBinding
    private val viewModel: DiscoverRecipesViewModel by viewModels { viewModelFactory }
    private var searchJob: Job? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (activity as MainActivity).mainComponent.inject(this)
    }

    @InternalCoroutinesApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment. View binding to optimize space, as no data binding used
        binding = FragmentDiscoverRecipesBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initAdapter()

        //preparing search functionality
        val query = savedInstanceState?.getString(LAST_SEARCH_QUERY) ?: DEFAULT_SEARCH_QUERY
        search(query)
        initSearch(query)


        // observing navigation state and navigating to detail page
        viewModel.navigateToSelectedRecipe.observe(viewLifecycleOwner, {
            if ( null != it) {
                this.findNavController().navigate(
                    DiscoverRecipesFragmentDirections.actionDiscoverRecipesFragmentToDiscoverRecipeDetailFragment(it))
                viewModel.displayRecipeDetailsComplete()
            }
        })

        // enable Retry connection method
        binding.retryButton.setOnClickListener { adapter.retry() }

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

//        setupItemDecoration()
        addListDividerDecoration()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_nav_menu_discover, menu)
        toolbar.overflowIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_search)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Timber.i("onOptionsItemSelected")
        when (item.itemId) {
            R.id.search_menu_item -> {
                Timber.i("search selected")
                hideShowSearchBar()
            }
            R.id.search_by_ingredient_menu_item -> {
                Timber.i("search selected")
                hideShowSearchBar()
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun initAdapter() {
        // set onClick listener to enable clicks on item
        adapter = DiscoverRecipesAdapter(DiscoverRecipesAdapter.OnClickListener {
            viewModel.displayRecipeDetails(it)
        })

        //adding loadState adapter
        binding.discoverListRecyclerview.adapter = adapter.withLoadStateFooter(
            footer = RecipeLoadStateAdapter { adapter.retry() }
        )
        adapter.addLoadStateListener { loadState ->
            // Only show the RV if refresh succeeds.
            binding.discoverListRecyclerview.isVisible = loadState.source.refresh is LoadState.NotLoading
            // Show loading spinner during initial load or refresh.
            binding.progressBar.isVisible = loadState.source.refresh is LoadState.Loading
            // Show the retry state if initial load or refresh fails.
            binding.retryButton.isVisible = loadState.source.refresh is LoadState.Error
        }
    }

    // to start search job + update adapter
    private fun search(searchQuery: String) {
        searchJob?.cancel()
        Timber.i("search method initiated $searchQuery")
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
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


    private fun addListDividerDecoration() {
        //adding list divider decorations
        val heightInPixels = resources.getDimensionPixelSize(R.dimen.list_item_divider_height)
        binding.discoverListRecyclerview.addItemDecoration(
            DividerItemDecoration(
                R.color.Text,
                heightInPixels
            )
        )
    }

    private fun hideShowSearchBar() {
        binding.discoverSearchInput.visibility = if (discover_search_input.visibility == View.GONE) View.VISIBLE else View.GONE
    }
}