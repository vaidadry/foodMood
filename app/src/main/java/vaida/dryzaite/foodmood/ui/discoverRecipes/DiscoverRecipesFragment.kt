package vaida.dryzaite.foodmood.ui.discoverRecipes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import vaida.dryzaite.foodmood.R

class DiscoverRecipesFragment : Fragment() {

    companion object {
        fun newInstance() = DiscoverRecipesFragment()
    }

    private lateinit var viewModel: DiscoverRecipesViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_discover_recipes, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DiscoverRecipesViewModel::class.java)
        // TODO: Use the ViewModel
    }

}