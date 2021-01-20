package vaida.dryzaite.foodmood.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import vaida.dryzaite.foodmood.database.RecipeDao
import vaida.dryzaite.foodmood.database.RecipeDatabase
import vaida.dryzaite.foodmood.utilities.DATABASE_NAME

@InstallIn(ActivityComponent::class)
@Module
object DatabaseModule {
    @ActivityScoped
    @Provides
    fun provideRecipeDatabase(@ActivityContext appContext: Context): RecipeDatabase =
        Room.databaseBuilder(
            appContext,
            RecipeDatabase::class.java,
            DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .build()

    @ActivityScoped
    @Provides
    fun provideRecipeDao(database: RecipeDatabase): RecipeDao {
        return database.recipeDao()
    }
}