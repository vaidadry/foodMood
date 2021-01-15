package vaida.dryzaite.foodmood.ui.recipeList

import android.os.Bundle
import android.view.View
import android.view.MenuInflater
import android.view.MenuItem
import android.view.Menu
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.chip.Chip
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipe_list.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentRecipeListBinding
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.ui.BaseFragment
import vaida.dryzaite.foodmood.ui.NavigationSettings
import vaida.dryzaite.foodmood.utilities.*

@AndroidEntryPoint
class RecipeListFragment : BaseFragment<RecipeListViewModel, FragmentRecipeListBinding>(), RecipeListAdapter.RecipeListAdapterListener {
    override val navigationSettings: NavigationSettings? = null
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

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        setupNavigation()
        setupAdapter()
        setupRecyclerView()
        setupItemTouchHelper()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_nav_menu, menu)

        // clicks on icons in toolbar
        val favoriteIcon = menu.findItem(R.id.favorite_menu_item)
        favoriteIcon.actionView.setOnClickListener {
            menu.performIdentifierAction(
                favoriteIcon.itemId,
                0
            )
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite_menu_item -> {
                navigateToFavoritesPage()
            }
        }
        return super.onOptionsItemSelected(item)
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
                this.findNavController().navigate(RecipeListFragmentDirections.actionRecipeListFragmentToAddRecipeFragment(null))
                viewModel.onFabClicked()
            }
        })
    }

    private fun setupObservers() {
        binding.chipMealTypeSelection.setOnCheckedChangeListener { _, checkedId ->
            val titleOrNull = chip_meal_type_selection.findViewById<Chip>(checkedId)?.text.toString()
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
        viewModel.mealSelection.observe(viewLifecycleOwner, {
            viewModel.initFilter().observe(viewLifecycleOwner, { recipes ->
                recipes?.let {
                    adapter.recipes = it
                    checkForEmptyState()
                }
            })
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
        itemTouchHelper.attachToRecyclerView(recipe_list_recyclerview)
    }

    // TODO - shows after selection with items
    private fun checkForEmptyState() {
        binding.emptyState.isInvisible = adapter.itemCount != 0
    }

    private fun navigateToFavoritesPage() {
        this.findNavController().navigate(
            RecipeListFragmentDirections.actionRecipeListFragmentToFavoritesFragment()
        )
        viewModel.onRecipeDetailNavigated()
    }
}
