package vaida.dryzaite.foodmood.di

import dagger.Binds
import dagger.Module
import vaida.dryzaite.foodmood.repository.RecipeDatabaseRepository
import vaida.dryzaite.foodmood.repository.RecipeRepository
import javax.inject.Singleton

@Module
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun provideRepository(repository: RecipeDatabaseRepository): RecipeRepository
}