package vaida.dryzaite.foodmood.ui.recipePage

import android.content.Intent
import android.net.Uri
import android.view.MenuItem
import android.widget.CheckBox
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentRecipeDetailBinding
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.ui.BaseFragment
import vaida.dryzaite.foodmood.ui.NavigationSettings
import vaida.dryzaite.foodmood.utilities.convertNumericMealTypeToString

@AndroidEntryPoint
class RecipeFragment: BaseFragment<RecipeViewModel, FragmentRecipeDetailBinding>(), Toolbar.OnMenuItemClickListener {

    override val navigationSettings: NavigationSettings? = null
    override val layoutId: Int = R.layout.fragment_recipe_detail
    private val args by navArgs<RecipeFragmentArgs>()

    override fun getViewModelClass(): Class<RecipeViewModel> {
        return RecipeViewModel::class.java
    }

    override fun setupUI() {
        setupObservers()
        viewModel.setRecipe(args.recipeEntry)
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

        binding.toolbarRecipe.setOnMenuItemClickListener(this)
        val favoriteMenuItem = binding.toolbarRecipe.menu.findItem(R.id.favorite_item_selector)
        val checkbox = favoriteMenuItem.actionView as CheckBox
        // fav button clicks
        viewModel.recipe.observe(viewLifecycleOwner, {
            if (it != null) {
                setupFavoriteToggle(checkbox, it)
            }
        })

        viewModel.notInDatabase.observe(viewLifecycleOwner, {
            binding.buttonAddToDatabase.isVisible = it ?: false
            binding.detailMeal.isGone = it ?: false
        })

        viewModel.navigateToUrl.observe(viewLifecycleOwner, {
            it?.let {
                redirectToRecipeUrl(it)
                viewModel.onButtonClicked()
            }
        })

        //observer to navigate to Add recipe fragment, passing info via Safe Args
        viewModel.navigateToAddRecipe.observe(viewLifecycleOwner, {
            if (it != null) {
                this.findNavController().navigate(
                    RecipeFragmentDirections.actionRecipeFragmentToAddRecipeFragment(it)
                )
                viewModel.onClickAddFragmentComplete()
            }
        })
    }

    private fun getShareIntent(): Intent {
        val recipe = viewModel.recipe.value!!
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT,
            getString(R.string.share_message, recipe.title, convertNumericMealTypeToString(recipe.meal, resources), recipe.href))
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