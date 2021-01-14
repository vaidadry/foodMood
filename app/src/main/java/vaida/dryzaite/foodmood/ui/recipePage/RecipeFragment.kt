package vaida.dryzaite.foodmood.ui.recipePage

import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import android.widget.CheckBox
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_recipe_detail.*
import timber.log.Timber
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentRecipeDetailBinding
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.RecipeGenerator
import vaida.dryzaite.foodmood.ui.BaseFragment
import vaida.dryzaite.foodmood.ui.NavigationSettings
import vaida.dryzaite.foodmood.utilities.convertNumericMealTypeToString

@AndroidEntryPoint
class RecipeFragment constructor(private val generator: RecipeGenerator) : BaseFragment<RecipeViewModel, FragmentRecipeDetailBinding>(), Toolbar.OnMenuItemClickListener {

    override val navigationSettings: NavigationSettings? = null
    override val layoutId: Int = R.layout.fragment_recipe_detail
    private val args by navArgs<RecipeFragmentArgs>()

    override fun getViewModelClass(): Class<RecipeViewModel> {
        return RecipeViewModel::class.java
    }

    override fun setupUI() {
        val recipeEntry = generator.generateRecipe(
            title = args.recipeEntry.title,
            meal = 0,
            recipe = args.recipeEntry.href,
            ingredients = args.recipeEntry.ingredients
        )
        viewModel.setRecipe(recipeEntry) // todo sulyginti Api duomenis i Recipe
        setupObservers()
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

    private fun setupObservers() {
        toolbar_item.setOnMenuItemClickListener(this)
        val favoriteMenuItem = toolbar_item.menu.findItem(R.id.favorite_item_selector)
        val checkbox = favoriteMenuItem.actionView as CheckBox
        // fav button clicks
        viewModel.recipe.observe(viewLifecycleOwner, {
            if (it != null) {
                setupFavoriteToggle(checkbox, it)
            }
        })

        viewModel.navigateToUrl.observe(viewLifecycleOwner, Observer {
            it?.let {
                redirectToRecipeUrl(it)
                viewModel.onButtonClicked()
            }
        })
    }

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

    private fun redirectToRecipeUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        view?.context?.startActivity(intent)
    }
}