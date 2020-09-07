package vaida.dryzaite.foodmood.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import kotlinx.coroutines.ExperimentalCoroutinesApi
import vaida.dryzaite.foodmood.model.RecipeGenerator
import vaida.dryzaite.foodmood.repository.RecipeRepository
import vaida.dryzaite.foodmood.ui.ViewModelFactory
import vaida.dryzaite.foodmood.ui.addRecipe.AddRecipeViewModel
import vaida.dryzaite.foodmood.ui.addRecipe.AddRecipeViewModel2
import vaida.dryzaite.foodmood.ui.discoverRecipes.DiscoverRecipesViewModel
import vaida.dryzaite.foodmood.ui.favoritesPage.FavoritesViewModel
import vaida.dryzaite.foodmood.ui.homePage.HomeViewModel
import vaida.dryzaite.foodmood.ui.recipeList.RecipeListViewModel
import vaida.dryzaite.foodmood.ui.recipePage.RecipeViewModel

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(AddRecipeViewModel::class)
    abstract fun bindAddRecipeViewModel(viewModel: AddRecipeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AddRecipeViewModel2::class)
    abstract fun bindAddRecipe2ViewModel(viewModel: AddRecipeViewModel2): ViewModel

    @ExperimentalCoroutinesApi
    @Binds
    @IntoMap
    @ViewModelKey(DiscoverRecipesViewModel::class)
    abstract fun bindDiscoverRecipesViewModel(viewModel: DiscoverRecipesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FavoritesViewModel::class)
    abstract fun bindFavoritesViewModel(viewModel: FavoritesViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecipeListViewModel::class)
    abstract fun bindRecipeListViewModel(viewModel: RecipeListViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RecipeViewModel::class)
    abstract fun bindRecipeViewModel(viewModel: RecipeViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}