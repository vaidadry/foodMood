package vaida.dryzaite.foodmood.ui.recipeList

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.jakewharton.rxbinding2.widget.textChanges
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_recipe_list.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.utilities.ItemTouchHelperCallback
import vaida.dryzaite.foodmood.utilities.RecipeDiffCallback
import vaida.dryzaite.foodmood.viewmodel.RecipeListViewModel
import java.util.concurrent.TimeUnit

class RecipeListFragment : Fragment(), RecipeListAdapter.RecipeListAdapterListener {

    private lateinit var viewModel: RecipeListViewModel
    private val disposable = CompositeDisposable()

        private val adapter = RecipeListAdapter(mutableListOf(), this)
//    private val adapter = RecipeListAdapter(viewModel.oldFilteredResults, this) // 1


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
        Log.i("menu item", "onOptionsItemSelected")
        when (item.itemId) {
            R.id.favorite_menu_item -> {
                Log.i("menu item", "favorite selected")
            }
            R.id.search_menu_item -> {
                Log.i("menu item", "search selected")
                hideShowSearchBar()

            }
            R.id.listDisplay -> {
                Log.i("view type", "LIST")
//                showListView()
            }
            R.id.gridDisplay -> {
                Log.i("view type", "GRID")
//                showGridView()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        setupViews()

        viewModel = ViewModelProvider(this).get(RecipeListViewModel::class.java)
        
        setupRecyclerView()
        getSearchInput()
        setupItemTouchHelper()

        //updating Live data observer  with ViewModel data
        viewModel.getAllRecipesLiveData().observe(viewLifecycleOwner, Observer { recipes ->
            recipes?.let {
                adapter.updateRecipes(recipes)
            }
            checkForEmptyState()
        })

        addListDividerDecoration()
    }

    override fun deleteRecipeAtPosition(recipe: RecipeEntry) {
        viewModel.deleteRecipe(recipe)
    }

    private fun addListDividerDecoration() {
        //adding list divider decorations
        val heightInPixels = resources.getDimensionPixelSize(R.dimen.list_item_divider_height)
        recipe_list_recyclerview.addItemDecoration(
            DividerItemDecoration(
                R.color.Text,
                heightInPixels
            )
        )
    }

    private fun setupRecyclerView() {
        //setting up a Recyclerview
        val layoutManager = LinearLayoutManager(context)
        recipe_list_recyclerview.layoutManager = layoutManager
        recipe_list_recyclerview.adapter = adapter
    }


    fun setupViews() {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_recipeListFragment_to_addRecipeFragment)
        }
    }

    // setting up drag&drop move
    private fun setupItemTouchHelper() {
        val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recipe_list_recyclerview)
    }

    //if no items, empty state text is shown
    private fun checkForEmptyState() {
        emptyState.visibility = if (adapter.itemCount == 0) View.VISIBLE else View.INVISIBLE
    }

//
//    private fun showListView() {
//        layoutManager.spanCount = 1
//    }
//    private fun showGridView() {
//        layoutManager.spanCount = 2
//    }

    private fun hideShowSearchBar() {
        search_bar.visibility = if (search_bar.visibility == View.GONE) View.VISIBLE else View.GONE
    }

    private fun getSearchInput() {
        search_input.textChanges().debounce(200, TimeUnit.MILLISECONDS).subscribe {
            viewModel.search(it.toString()).subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe {
                    val diffResult = DiffUtil.calculateDiff(
                        RecipeDiffCallback(viewModel.oldFilteredResults, viewModel.filteredResults))
                    viewModel.oldFilteredResults.clear()
                    viewModel.oldFilteredResults.addAll(viewModel.filteredResults)
                    diffResult.dispatchUpdatesTo(adapter)
                }.addTo(disposable)
        }.addTo(disposable)

    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

}
