package vaida.dryzaite.foodmood.di

import android.content.Context
import dagger.Module
import dagger.Provides
import vaida.dryzaite.foodmood.FoodmoodApplication
import vaida.dryzaite.foodmood.database.RecipeDao
import vaida.dryzaite.foodmood.database.RecipeDatabase
import javax.inject.Singleton

@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(appContext: Context): RecipeDatabase {
        return RecipeDatabase.getInstance(appContext)
    }

    @Singleton
    @Provides
    fun provideRecipeDao(database: RecipeDatabase): RecipeDao {
        return database.recipeDao()
    }
}