package vaida.dryzaite.foodmood.app

import android.app.Application
import timber.log.Timber

class FoodmoodApplication: Application() {

//    companion object {
//       lateinit var database: RecipeDatabase
//    }

    override fun onCreate() {
        super.onCreate()
        // initializing Timber for Logging
        Timber.plant(Timber.DebugTree())
        //initializing database
//        database = Room.databaseBuilder(this, RecipeDatabase::class.java, "recipe_database").build()
    }
}
