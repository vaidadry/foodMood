package vaida.dryzaite.foodmood.ui

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import vaida.dryzaite.foodmood.model.RecipeGenerator
import vaida.dryzaite.foodmood.ui.discoverRecipes.DiscoverRecipesAdapter
import vaida.dryzaite.foodmood.ui.discoverRecipes.DiscoverRecipesFragment
import vaida.dryzaite.foodmood.ui.discoverRecipes.DiscoverRecipesIngredientFragment
import javax.inject.Inject

class MainActivityFragmentFactory @Inject constructor(
    private val discoverRecipesAdapter: DiscoverRecipesAdapter,
    private val generator: RecipeGenerator
): FragmentFactory() {

    @ExperimentalCoroutinesApi
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className) {
            DiscoverRecipesFragment::class.java.name -> DiscoverRecipesFragment(discoverRecipesAdapter, generator)
            DiscoverRecipesIngredientFragment::class.java.name -> DiscoverRecipesIngredientFragment(discoverRecipesAdapter,generator)
            else -> super.instantiate(classLoader, className)
        }
    }
}