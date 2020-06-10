package vaida.dryzaite.foodmood.model.room

import androidx.room.Database
import androidx.room.RoomDatabase
import vaida.dryzaite.foodmood.model.RecipeEntry

@Database (entities = [(RecipeEntry::class)], version = 1)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
}
