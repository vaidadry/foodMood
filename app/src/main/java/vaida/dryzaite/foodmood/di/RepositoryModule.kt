package vaida.dryzaite.foodmood.di

import com.google.firebase.crashlytics.FirebaseCrashlytics
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import vaida.dryzaite.foodmood.database.RecipeDao
import vaida.dryzaite.foodmood.network.RecipeApiService
import vaida.dryzaite.foodmood.repository.RecipeDatabaseRepository
import vaida.dryzaite.foodmood.repository.RecipeRepository

@InstallIn(ActivityComponent::class)
@Module
object RepositoryModule {

    @ActivityScoped
    @Provides
    fun provideRepository(
        dao: RecipeDao,
        service: RecipeApiService,
        firebaseCrashlytics: FirebaseCrashlytics
    ) = RecipeDatabaseRepository(dao, service, firebaseCrashlytics) as RecipeRepository
}