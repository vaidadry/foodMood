package vaida.dryzaite.foodmood.di

import dagger.Subcomponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import vaida.dryzaite.foodmood.ui.addRecipe.AddRecipeFragment
import vaida.dryzaite.foodmood.ui.addRecipe.AddRecipeFragment2
import vaida.dryzaite.foodmood.ui.discoverRecipePage.DiscoverRecipeDetailFragment
import vaida.dryzaite.foodmood.ui.discoverRecipes.DiscoverRecipesFragment
import vaida.dryzaite.foodmood.ui.discoverRecipes.DiscoverRecipesIngredientFragment
import vaida.dryzaite.foodmood.ui.favoritesPage.FavoritesFragment
import vaida.dryzaite.foodmood.ui.homePage.HomeFragment
import vaida.dryzaite.foodmood.ui.main.MainActivity
import vaida.dryzaite.foodmood.ui.recipeList.RecipeListFragment
import vaida.dryzaite.foodmood.ui.recipePage.RecipeFragment
import vaida.dryzaite.foodmood.ui.suggestionPage.SuggestionFragment

@Subcomponent(modules = [ViewModelModule::class])
interface MainComponent {

    //factory to create subComponent for Main activity
    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }

    fun inject(activity: MainActivity)
    fun inject(fragment: HomeFragment)
    fun inject(fragment: SuggestionFragment)
    fun inject(fragment: RecipeListFragment)
    fun inject(fragment: RecipeFragment)
    fun inject(fragment: AddRecipeFragment)
    fun inject(fragment: AddRecipeFragment2)
    fun inject(fragment: FavoritesFragment)
    @ExperimentalCoroutinesApi
    fun inject(fragment: DiscoverRecipesFragment)
    @ExperimentalCoroutinesApi
    fun inject(fragment: DiscoverRecipesIngredientFragment)
    fun inject(fragment: DiscoverRecipeDetailFragment)
}