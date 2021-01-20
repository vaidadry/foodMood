package vaida.dryzaite.foodmood.ui.recipeList

import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.core.view.isVisible
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentRecipeListBinding
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.ui.BaseFragment
import vaida.dryzaite.foodmood.ui.NavigationSettings
import vaida.dryzaite.foodmood.utilities.BUNDLE_KEY
import vaida.dryzaite.foodmood.utilities.DividerItemDecoration
import vaida.dryzaite.foodmood.utilities.ItemTouchHelperCallback
import vaida.dryzaite.foodmood.utilities.REQUEST_KEY

@AndroidEntryPoint
class RecipeListFragment : BaseFragment<RecipeListViewModel, FragmentRecipeListBinding>(), RecipeListAdapter.RecipeListAdapterListener {
    override val navigationSettings: NavigationSettings? by lazy {
        NavigationSettings(requireContext().getString(R.string.bottomNav_recipeBook))
    }
    override val layoutId: Int = R.layout.fragment_recipe_list
    private lateinit var adapter: RecipeListAdapter

    // handles back button clicks not to return to add-form
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                findNavController().navigate(RecipeListFragmentDirections.actionRecipeListFragmentToHomeFragment2())
            }
        })
    }

    override fun getViewModelClass(): Class<RecipeListViewModel> {
        return RecipeListViewModel::class.java
    }

    override fun setupUI() {
        setupObservers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupToolbar()
        setupNavigation()
        setupAdapter()
        setupRecyclerView()
        setupItemTouchHelper()
    }

    private fun setupToolbar() {
        toolbar.apply {
            inflateMenu(R.menu.top_nav_menu)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.favorite_menu_item -> {
                        navigateToFavoritesPage()
                    }
                }
                true
            }
        }
    }

    override fun deleteRecipeAtPosition(recipe: RecipeEntry) {
        viewModel.onDeleteRecipe(recipe)
    }

    override fun addFavorites(recipe: RecipeEntry) {
        viewModel.addFavorites(recipe)
    }

    override fun removeFavorites(recipe: RecipeEntry) {
        viewModel.removeFavorites(recipe)
    }

    private fun addListDividerDecoration() {
        val heightInPixels = resources.getDimensionPixelSize(R.dimen.list_item_divider_height)
        binding.recipeListRecyclerview.addItemDecoration(
            DividerItemDecoration(
                R.color.Text,
                heightInPixels
            )
        )
    }

    private fun setupAdapter() {
        adapter = RecipeListAdapter(this, RecipeListOnClickListener { recipe ->
            viewModel.onRecipeClicked(recipe)
        })
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        binding.recipeListRecyclerview.layoutManager = layoutManager
        binding.recipeListRecyclerview.adapter = adapter
        addListDividerDecoration()
    }

    private fun setupNavigation() {
        viewModel.navigateToAddRecipeFragment.observe(viewLifecycleOwner, {
            if (it == true) {
                this.findNavController().navigate(
                    RecipeListFragmentDirections.actionRecipeListFragmentToAddRecipeFragment(
                        null
                    )
                )
                viewModel.onFabClicked()
            }
        })
    }

    private fun setupObservers() {
        binding.chipMealTypeSelection.setOnCheckedChangeListener { _, checkedId ->
            val titleOrNull = binding.chipMealTypeSelection.findViewById<Chip>(checkedId)?.text.toString()
            viewModel.onMealSelected(titleOrNull, resources)
        }

        // if changes in Favorites fragment, updates RV
        setFragmentResultListener(REQUEST_KEY) { _, bundle ->
            val result = bundle.getBoolean(BUNDLE_KEY)
            if (result) {
                viewModel.allRecipes.observe(viewLifecycleOwner, { recipes ->
                    recipes?.let {
                        adapter.recipes = it
                    }
                })
            }
        }

        // filtering applied
        viewModel.initFilter().observe(viewLifecycleOwner, { recipes ->
            recipes?.let {
                checkForEmptyState(it)
                adapter.recipes = it
            }
        })

        viewModel.navigateToRecipeDetail.observe(viewLifecycleOwner, { keyId ->
            keyId?.let {
                this.findNavController().navigate(
                    RecipeListFragmentDirections.actionRecipeListFragmentToRecipeFragment(keyId)
                )
                viewModel.onRecipeDetailNavigated()
            }
        })

        // fav button state
        viewModel.favoriteStatusChange.observe(viewLifecycleOwner, {
            if (it == true) {
                viewModel.onFavoriteClickCompleted()
            }
        })
    }

    // drag&drop move
    private fun setupItemTouchHelper() {
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.recipeListRecyclerview)
    }

    private fun checkForEmptyState(recipes: List<RecipeEntry>?) {
        binding.emptyState.isVisible = recipes.isNullOrEmpty()
    }

    private fun navigateToFavoritesPage() {
        this.findNavController().navigate(
            RecipeListFragmentDirections.actionRecipeListFragmentToFavoritesFragment()
        )
        viewModel.onRecipeDetailNavigated()
    }
}
