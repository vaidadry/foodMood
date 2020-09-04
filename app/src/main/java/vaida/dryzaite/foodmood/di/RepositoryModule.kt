package vaida.dryzaite.foodmood.di

import dagger.Binds
import dagger.Module
import vaida.dryzaite.foodmood.repository.RecipeDatabaseRepository
import vaida.dryzaite.foodmood.repository.RecipeRepository

@Module
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(repository: RecipeDatabaseRepository): RecipeRepository
}