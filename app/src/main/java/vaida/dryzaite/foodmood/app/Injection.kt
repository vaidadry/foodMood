package vaida.dryzaite.foodmood.app

import android.app.Application
import vaida.dryzaite.foodmood.model.room.RecipeDao
import vaida.dryzaite.foodmood.model.room.RecipeRepository
import vaida.dryzaite.foodmood.model.room.RecipeDatabaseRepository

object Injection {
    fun provideRecipeRepository(application: Application): RecipeRepository =
        RecipeDatabaseRepository(application)
}