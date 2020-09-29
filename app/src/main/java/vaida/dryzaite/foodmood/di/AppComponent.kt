package vaida.dryzaite.foodmood.di

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import vaida.dryzaite.foodmood.ui.main.SplashActivity
import javax.inject.Singleton

// Main App container
@Singleton
@Component(modules = [AppSubcomponents::class, RepositoryModule::class, DatabaseModule::class, ServiceModule::class])
interface AppComponent {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun mainComponent(): MainComponent.Factory
    fun inject(activity: SplashActivity)
}