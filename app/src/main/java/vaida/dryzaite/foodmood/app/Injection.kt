package vaida.dryzaite.foodmood.app

import android.app.Application
import vaida.dryzaite.foodmood.data.RecipeRepository
import vaida.dryzaite.foodmood.data.RecipeDatabaseRepository
//import vaida.dryzaite.foodmood.network.RecipeApi
import vaida.dryzaite.foodmood.network.RecipeApiService

object Injection {
    fun provideRecipeRepository(application: Application): RecipeRepository =
        RecipeDatabaseRepository(application, RecipeApiService.create())
}