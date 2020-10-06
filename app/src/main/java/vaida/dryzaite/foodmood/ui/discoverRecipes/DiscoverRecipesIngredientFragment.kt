package vaida.dryzaite.foodmood.ui.discoverRecipes

import android.os.Bundle
import android.view.*
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.view.inputmethod.EditorInfo
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.android.flexbox.FlexboxLayout
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_discover_recipes.toolbar
import kotlinx.android.synthetic.main.fragment_discover_recipes_ingredient.*
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
import vaida.dryzaite.foodmood.databinding.FragmentDiscoverRecipesIngredientBinding
import vaida.dryzaite.foodmood.utilities.DividerItemDecoration
import javax.inject.Inject

@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DiscoverRecipesIngredientFragment @Inject constructor(
    val adapter: DiscoverRecipesAdapter
): Fragment() {

    private lateinit var binding: FragmentDiscoverRecipesIngredientBinding
    private val viewModel: DiscoverRecipesIngredientViewModel by viewModels()
    private var searchJob: Job? = null



    @InternalCoroutinesApi
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        // Inflate the layout for this fragment. View binding to optimize space, as no data binding used
        binding = FragmentDiscoverRecipesIngredientBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        initAdapter()
        setupIngredientsInputListeners()

        // observing navigation state and navigating to detail page
        viewModel.navigateToSelectedRecipe.observe(viewLifecycleOwner, {
            if ( null != it) {
                this.findNavController().navigate(
                    DiscoverRecipesIngredientFragmentDirections.actionDiscoverRecipesIngredientFragmentToDiscoverRecipeDetailFragment(it))
                viewModel.displayRecipeDetailsComplete()
            }
        })

//        observing ingredients list and creating chips accordingly
        viewModel.ingredientsList.observe(viewLifecycleOwner, object : Observer<List<String>> {
            override fun onChanged(data: List<String>?) {
                data ?: return

                val chipGroup = binding.ingredientsGroupFL
                chipGroup.removeAllViews()

                for (chip in data) {
                    addNewChip(chip, chipGroup)
                }
                searchByIngredients(data)
            }
        })

        // enable Retry connection method
        binding.retryButton.setOnClickListener { adapter.retry() }

        return binding.root
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
                this.findNavController().navigate(DiscoverRecipesIngredientFragmentDirections.actionDiscoverRecipesIngredientFragmentToDiscoverRecipesFragment())
            }
            R.id.search_by_ingredient_menu_item -> {
                Timber.i("search by ingredient selected")

                hideShowSearchByIngredientBar()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initAdapter() {
        // set onClick listener to enable clicks on item
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


    private fun hideShowSearchByIngredientBar() {
        binding.ingredientSearchLayout.visibility = if (ingredient_search_layout.visibility == View.GONE) View.VISIBLE else View.GONE
    }


    // setting up text input listeners
    @InternalCoroutinesApi
    private fun setupIngredientsInputListeners() {
        binding.ingredientsInputET.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                addInputToIngredientsList()
                true
            } else {
                false
            }
        }
        binding.ingredientsInputET.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                addInputToIngredientsList()
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

    private fun addInputToIngredientsList()  {
        Timber.i("addInputToIngredientsList() initiated")
        binding.ingredientsInputET.text.trim().let {
            if (it.isNotEmpty() && !viewModel.ingredientsList.value?.contains(it)!!) {
                viewModel.addIngredientToSearchList(it.toString())
                Timber.i("chip added, and ingredient {it added to vm: ${viewModel.ingredientsList.value}")
            }
        }
        binding.ingredientsInputET.text.clear()
    }

    private fun addNewChip(ingredient: String, chipGroup: FlexboxLayout) {
        val chip = layoutInflater.inflate(R.layout.chip, chipGroup, false) as Chip
        chip.text = ingredient
        chip.isCloseIconVisible = true
        chip.isClickable = true
        chipGroup.addView(chip as View, chipGroup.childCount - 1)
        chip.setOnCloseIconClickListener {
            removeChip(it, chipGroup)

            viewModel.removeIngredientFromSearchList(ingredient)
            Timber.i("chip removed, and ingredient $ingredient removed to vm: ${viewModel.ingredientsList.value}")
        }
    }

    private fun removeChip(chip: View, chipGroup: FlexboxLayout) {
        val anim = AlphaAnimation(1f,0f)
        anim.duration = 250
        anim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationRepeat(animation: Animation?) {}

            override fun onAnimationEnd(animation: Animation?) {
                chipGroup.removeView(chip)
            }

            override fun onAnimationStart(animation: Animation?) {}
        })

        chip.startAnimation(anim)
    }

    // to start search job + update adapter
    private fun searchByIngredients(queryList: List<String>) {
        searchJob?.cancel()
        Timber.i("search method initiated $queryList")
        searchJob = viewLifecycleOwner.lifecycleScope.launch {
            Timber.i("search job started")
            viewModel.searchExternalRecipesByIngredient(queryList).collectLatest {
                Timber.i("paging data returned : $it")
                adapter.submitData(it)

            }
        }
    }

    //if no items, empty state text is shown
    private fun checkForEmptyState() {
        binding.emptyState.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.INVISIBLE
    }


    }