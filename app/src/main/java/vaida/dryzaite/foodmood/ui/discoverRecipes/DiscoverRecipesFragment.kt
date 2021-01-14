package vaida.dryzaite.foodmood.ui.discoverRecipes

import android.os.Bundle
import android.view.*
import android.view.inputmethod.EditorInfo
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import dagger.hilt.android.AndroidEntryPoint
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
import vaida.dryzaite.foodmood.ui.BaseFragment
import vaida.dryzaite.foodmood.ui.NavigationSettings
import vaida.dryzaite.foodmood.utilities.DividerItemDecoration
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DiscoverRecipesFragment @Inject constructor(
    val adapter: DiscoverRecipesAdapter
) : BaseFragment<DiscoverRecipesViewModel, FragmentDiscoverRecipesBinding>() {

    override val navigationSettings: NavigationSettings? = null
    override val layoutId: Int = R.layout.fragment_discover_recipes
    private var searchJob: Job? = null

    override fun getViewModelClass(): Class<DiscoverRecipesViewModel> {
        return DiscoverRecipesViewModel::class.java
    }

    @InternalCoroutinesApi
    override fun setupUI() {
        initAdapter()
        setupTitleInputListeners()
        setupObservers()
        binding.retryButton.setOnClickListener { adapter.retry() }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)

        setHasOptionsMenu(true)

        addListDividerDecoration()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_nav_menu_discover, menu)
        toolbar.overflowIcon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_search)
    }

    @InternalCoroutinesApi
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.search_menu_item -> {
                Timber.i("search by title selected")

                hideShowSearchByTitleBar()
            }
            R.id.search_by_ingredient_menu_item -> {
                Timber.i("search by ingredient selected")

                this.findNavController().navigate(
                    DiscoverRecipesFragmentDirections.actionDiscoverRecipesFragmentToDiscoverRecipesIngredientFragment())
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setupObservers() {
        viewModel.navigateToSelectedRecipe.observe(viewLifecycleOwner, {
            if ( null != it) {
                this.findNavController().navigate(
                    DiscoverRecipesFragmentDirections.actionDiscoverRecipesFragmentToDiscoverRecipeDetailFragment(it))
                viewModel.displayRecipeDetailsComplete()
            }
        })

        viewModel.currentTitleSearchQuery.observe(viewLifecycleOwner, {
            Timber.i("currentTitleSearchQuery CHANGE observed, triggers search: $it")
            it?.let {
                binding.titleInputET.setText(it)
                searchByTitle(it)
            }
        })
    }

    private fun initAdapter() {
        adapter.setItemClickedListener {
            viewModel.displayRecipeDetails(it)
        }

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
    private fun searchByTitle(searchQuery: String) {
        searchJob?.cancel()
        Timber.i("search method initiated $searchQuery")
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            Timber.i("search job started")
            viewModel.searchExternalRecipesByTitle(searchQuery).collectLatest {
                Timber.i("paging data returned : $it")
                adapter.submitData(it)

            }
            Timber.i("search job finished")
        }
    }

    private fun addInputToTitleSearchQueryString() {
        Timber.i("addInputToTitleSearchQueryString() initiated, previous search q was: ${viewModel.currentTitleSearchQuery.value}")
        binding.titleInputET.text.trim().let {
            if (it.isNotEmpty() && viewModel.currentTitleSearchQuery.value != it.toString())  {
                viewModel.updateTitleSearchQuery(it.toString())
                Timber.i("text added to vm: ${viewModel.currentTitleSearchQuery.value}")
            }
        }
    }

    @InternalCoroutinesApi
    private fun setupTitleInputListeners() {

        binding.titleInputET.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                addInputToTitleSearchQueryString()
                true
            } else {
                false
            }
        }
        binding.titleInputET.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                addInputToTitleSearchQueryString()
                true
            } else {
                false
            }
        }

        // Scroll to top when the list is refreshed from network.
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

    private fun hideShowSearchByTitleBar() {
        binding.discoverSearchInputTitle.visibility = if (discover_search_input_title.visibility == View.GONE) View.VISIBLE else View.GONE
    }

    // TODO - set up
    private fun checkForEmptyState() {
        binding.emptyState.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.INVISIBLE
    }
}