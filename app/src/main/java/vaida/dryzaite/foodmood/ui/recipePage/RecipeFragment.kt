package vaida.dryzaite.foodmood.ui.recipePage

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import timber.log.Timber
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.RecipeFragmentBinding
import vaida.dryzaite.foodmood.utilities.convertNumericMealTypeToString


class RecipeFragment : Fragment() {

    private lateinit var recipeViewModel: RecipeViewModel
    private lateinit var binding: RecipeFragmentBinding
    private lateinit var viewModelFactory: RecipeViewModelFactory

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = RecipeFragmentBinding.inflate(inflater, container, false)

        val application = requireNotNull(this.activity).application
        val arguments = RecipeFragmentArgs.fromBundle(requireArguments())

        viewModelFactory = RecipeViewModelFactory(arguments.keyId, application)
        recipeViewModel = ViewModelProvider(this, viewModelFactory).get(RecipeViewModel::class.java)

        binding.recipeViewModel = recipeViewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
        }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //enable toolbar
        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarItem)
        setHasOptionsMenu(true)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_nav_menu_item, menu)

        //setup clicks on icons in toolbar
        val shareItem = menu.findItem(R.id.menu_share_item)
        shareItem.actionView.setOnClickListener {
            menu.performIdentifierAction(
                shareItem.itemId,
                0
            )
            Timber.i("menu item clicked")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_share_item -> {
                Timber.i("executing Share Recipe intent")
                shareRecipe()}
        }
        return super.onOptionsItemSelected(item)
    }




//    intent for sharing a recipe via other apps
    private fun getShareIntent(): Intent {
        val recipe = recipeViewModel.recipeDetail.value!!
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT,
            getString(R.string.share_message, recipe.title, convertNumericMealTypeToString(recipe.meal, resources), recipe.recipe))
        return shareIntent
    }

    private fun shareRecipe() {
        startActivity(getShareIntent())
    }




}