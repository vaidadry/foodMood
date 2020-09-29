package vaida.dryzaite.foodmood.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.RemoteKeys
import vaida.dryzaite.foodmood.network.ExternalRecipe

@Database (entities = [RecipeEntry::class, ExternalRecipe::class, RemoteKeys::class], version = 9, exportSchema = false)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun externalRecipesDao():ExternalRecipesDao
    abstract fun remoteKeysDao(): RemoteKeysDao


    // The value of a volatile variable will never be cached,
    // and all writes and reads will be done to and from the main memory.

    //boilerplate code - can be used in all projects
    companion object {
        @Volatile
        private var INSTANCE: RecipeDatabase? = null

        fun getInstance(context: Context): RecipeDatabase {
            //Wrapping the code to get the database into synchronized
            // means that only one thread of execution at a time can enter
            // this block of code, which makes sure the database only gets
            // initialized once.
            synchronized(this) {
                var instance = INSTANCE

                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        RecipeDatabase::class.java,
                        "foodmood_saved_recipes_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
