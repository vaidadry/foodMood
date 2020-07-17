package vaida.dryzaite.foodmood.ui.recipeList

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_recipe_list.*
import timber.log.Timber
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentRecipeListBinding
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.utilities.ItemTouchHelperCallback

class RecipeListFragment : Fragment(), RecipeListAdapter.RecipeListAdapterListener {

    private lateinit var recipeListViewModel: RecipeListViewModel
    private lateinit var binding: FragmentRecipeListBinding
    private lateinit var adapter: RecipeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecipeListBinding.inflate(inflater, container, false)

        //reference to context, to get database instance
        val application = requireNotNull(this.activity).application

        val viewModelFactory = RecipeListViewModelFactory(application)
        recipeListViewModel = ViewModelProvider(this, viewModelFactory).get(RecipeListViewModel::class.java)

        binding.lifecycleOwner = this
        binding.recipeListViewModel = recipeListViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        setupViews()

        setupAdapter()

        setupRecyclerView()

        setSearchInputListener()

        setupItemTouchHelper()

        //updating Live data observer with ViewModel data
        recipeListViewModel.getAllRecipesLiveData().observe(viewLifecycleOwner, Observer { recipes ->
            recipes?.let {
                adapter.updateRecipes(recipes)
            }
            checkForEmptyState()
        })
        addListDividerDecoration()

        //set up observer to react on item taps and enable navigation
        recipeListViewModel.navigateToRecipeDetail.observe(viewLifecycleOwner, Observer { keyId->
            keyId?.let {
                this.findNavController().navigate(
                    RecipeListFragmentDirections.actionRecipeListFragmentToRecipeFragment(keyId))
                recipeListViewModel.onRecipeDetailNavigated()
            }
        })

        //observer to react on fav button state (if change needed or not)
        recipeListViewModel.favoriteStatusChange.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                recipeListViewModel.onFavoriteClickCompleted()
            }
        })
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
        val favoriteIcon = menu.findItem(R.id.favorite_menu_item)
        favoriteIcon.actionView.setOnClickListener {
            menu.performIdentifierAction(
                favoriteIcon.itemId,
                0
            )
        }
    }

    //based on selected menu item, layout managers are switched
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Timber.i("onOptionsItemSelected")
        when (item.itemId) {
            R.id.favorite_menu_item -> {
                navigateToFavoritesPage()
                Timber.i("favorite selected")
            }
            R.id.search_menu_item -> {
                Timber.i("search selected")
                hideShowSearchBar()

            }
            R.id.list_display -> {
                Timber.i("LIST")
//                showListView()
            }
            R.id.grid_display -> {
                Timber.i( "GRID")
//                showGridView()
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun deleteRecipeAtPosition(recipe: RecipeEntry) {
        recipeListViewModel.onDeleteRecipe(recipe)
    }

    override fun addFavorites(recipe: RecipeEntry) {
        Timber.i("addFavorites called  ")
        recipeListViewModel.addFavorites(recipe)

    }

    override fun removeFavorites(recipe: RecipeEntry) {
        Timber.i("RemoveFavorites called  ")
        recipeListViewModel.removeFavorites(recipe)
    }


    private fun addListDividerDecoration() {
        //adding list divider decorations
        val heightInPixels = resources.getDimensionPixelSize(R.dimen.list_item_divider_height)
        binding.recipeListRecyclerview.addItemDecoration(
            DividerItemDecoration(
                R.color.Text,
                heightInPixels
            )
        )
    }


    private fun setupAdapter() {
        adapter = RecipeListAdapter(mutableListOf(), this, RecipeListOnClickListener { id ->
            recipeListViewModel.onRecipeClicked(id)
        })
    }

    //setting up a Recyclerview
    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(context)
        binding.recipeListRecyclerview.layoutManager = layoutManager
        binding.recipeListRecyclerview.adapter = adapter
    }


    private fun setupViews() {
        recipeListViewModel.navigateToAddRecipeFragment.observe(viewLifecycleOwner, Observer {
            if (it == true) {
                this.findNavController().navigate(R.id.action_recipeListFragment_to_addRecipeFragment)
                recipeListViewModel.onFabClicked()
            }
        })
    }

    // setting up drag&drop move
    private fun setupItemTouchHelper() {
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recipe_list_recyclerview)
    }

    //if no items, empty state text is shown
    private fun checkForEmptyState() {
        binding.emptyState.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.INVISIBLE
    }

    private fun hideShowSearchBar() {
        binding.searchInput.visibility = if (search_input.visibility == View.GONE) View.VISIBLE else View.GONE
    }


        private fun setSearchInputListener() {
            binding.searchInput.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter.filter.filter(newText)
                    return false
                }
            })
        }

    private fun navigateToFavoritesPage() {
        this.findNavController().navigate(
            RecipeListFragmentDirections.actionRecipeListFragmentToFavoritesFragment(""))
        recipeListViewModel.onRecipeDetailNavigated()
    }
}
