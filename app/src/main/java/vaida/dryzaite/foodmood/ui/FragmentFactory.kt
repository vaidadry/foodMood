package vaida.dryzaite.foodmood.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import vaida.dryzaite.foodmood.ui.discoverRecipes.DiscoverRecipesAdapter
import vaida.dryzaite.foodmood.ui.discoverRecipes.DiscoverRecipesFragment
import vaida.dryzaite.foodmood.ui.discoverRecipes.DiscoverRecipesIngredientFragment
import vaida.dryzaite.foodmood.ui.favoritesPage.FavoritesAdapter
import vaida.dryzaite.foodmood.ui.favoritesPage.FavoritesFragment
import vaida.dryzaite.foodmood.ui.recipeList.RecipeListAdapter
import vaida.dryzaite.foodmood.ui.recipeList.RecipeListFragment
import javax.inject.Inject

class MainActivityFragmentFactory @Inject constructor(
    private val discoverRecipesAdapter: DiscoverRecipesAdapter
//    private val favoritesAdapter: FavoritesAdapter,
//    private val recipeListAdapter: RecipeListAdapter
): FragmentFactory() {

    @ExperimentalCoroutinesApi
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            DiscoverRecipesFragment::class.java.name -> DiscoverRecipesFragment(discoverRecipesAdapter)
            DiscoverRecipesIngredientFragment::class.java.name -> DiscoverRecipesIngredientFragment(discoverRecipesAdapter)
//            RecipeListFragment::class.java.name -> RecipeListFragment(recipeListAdapter) // not sure how to with data binding and multiple listeners in adapters..
//            FavoritesFragment::class.java.name -> FavoritesFragment(favoritesAdapter)
            else -> super.instantiate(classLoader, className)
        }
    }
}