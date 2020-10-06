package vaida.dryzaite.foodmood.ui.recipePage

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipe_detail.*
import timber.log.Timber
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentRecipeDetailBinding
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.utilities.convertNumericMealTypeToString

@AndroidEntryPoint
class RecipeFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    private lateinit var binding: FragmentRecipeDetailBinding

    private val args by navArgs<RecipeFragmentArgs>()
    private val viewModel: RecipeViewModel by viewModels()


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_detail, container, false)

        viewModel.setRecipe(args.recipeEntry)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
        }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return  when (item?.itemId) {
            R.id.menu_share_item -> {
                shareRecipe()
                true
            }
            else -> false
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // inflate the toolbar menu with the heart, set up  observer, handling fav button toggle
        toolbar_item.setOnMenuItemClickListener(this)
        val favoriteMenuItem = toolbar_item.menu.findItem(R.id.favorite_item_selector)
        val checkbox = favoriteMenuItem.actionView as CheckBox

        //observer handling fav button clicks
        viewModel.recipe.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                setupFavoriteToggle(checkbox, it)
            }
        })

        //observer handling clicks on URL field
        viewModel.navigateToUrl.observe(viewLifecycleOwner, Observer {
            it?.let {
                redirectToRecipeUrl(it)
                viewModel.onButtonClicked()
            }
        })
    }


//    intent for sharing a recipe via other apps
    private fun getShareIntent(): Intent {
        val recipe = viewModel.recipe.value!!
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT,
            getString(R.string.share_message, recipe.title, convertNumericMealTypeToString(recipe.meal, resources), recipe.recipe))
        return shareIntent
    }

    private fun shareRecipe() {
        startActivity(getShareIntent())
    }


    //enable Favorite button
    private fun setupFavoriteToggle(checkBox: CheckBox, recipe: RecipeEntry) {
        checkBox.isChecked = recipe.isFavorite
        Timber.i("live data  get by id $recipe")
        checkBox.setOnCheckedChangeListener { _, boolean ->
            recipe.isFavorite = boolean
            viewModel.updateRecipe(recipe)
            Timber.i("live data  get by id $recipe")
        }
        checkBox.isChecked = recipe.isFavorite
    }


    //linking to URL
    private fun redirectToRecipeUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        view?.context?.startActivity(intent)
    }
}