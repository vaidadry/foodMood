package vaida.dryzaite.foodmood.app

import android.app.Application
import vaida.dryzaite.foodmood.model.roomRecipeBook.RecipeRepository
import vaida.dryzaite.foodmood.model.roomRecipeBook.RecipeDatabaseRepository

object Injection {
    fun provideRecipeRepository(application: Application): RecipeRepository =
        RecipeDatabaseRepository(application)
}