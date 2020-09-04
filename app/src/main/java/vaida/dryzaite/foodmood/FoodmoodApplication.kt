package vaida.dryzaite.foodmood

import android.app.Application
import timber.log.Timber
import vaida.dryzaite.foodmood.di.AppComponent
import vaida.dryzaite.foodmood.di.DaggerAppComponent

open class FoodmoodApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        // initializing Timber for Logging
        Timber.plant(Timber.DebugTree())
    }

    // Instance of the AppComponent that will be used by all the Activities in the project
    val appComponent: AppComponent by lazy {
        initializeComponent()
    }

    open fun initializeComponent(): AppComponent {
        Timber.d("Initializing AppComponent")
        // Creates an instance of AppComponent using its Factory constructor
        // We pass the applicationContext that will be used as Context in the graph
        return DaggerAppComponent.factory().create(applicationContext)
    }

}
