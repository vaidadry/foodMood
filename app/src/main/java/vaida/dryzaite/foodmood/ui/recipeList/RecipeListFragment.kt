package vaida.dryzaite.foodmood.ui.recipeList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_recipe_list.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.model.RecipeBook

class RecipeListFragment : Fragment() {

    private lateinit var adapter: RecipeListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()

        //setting up a Recyclerview
        adapter = RecipeListAdapter(RecipeBook.getRecipes())
        recipe_list_recyclerview.layoutManager = LinearLayoutManager(context)
        recipe_list_recyclerview.adapter = adapter


        //adding list divider decorations
        val heightInPixels = resources.getDimensionPixelSize(R.dimen.list_item_divider_height)
        recipe_list_recyclerview.addItemDecoration(DividerItemDecoration(R.color.Text, heightInPixels))
    }

    fun setupViews() {
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_recipeListFragment_to_addRecipeFragment)
        }
    }

}
