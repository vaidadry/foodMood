package vaida.dryzaite.foodmood.app

import android.app.Application
import androidx.room.Room
import vaida.dryzaite.foodmood.model.room.RecipeDatabase

class FoodmoodApplication: Application() {

    companion object {
       lateinit var database: RecipeDatabase
    }

    override fun onCreate() {
        super.onCreate()
        database = Room.databaseBuilder(this, RecipeDatabase::class.java, "recipe_database").build()
    }
}
