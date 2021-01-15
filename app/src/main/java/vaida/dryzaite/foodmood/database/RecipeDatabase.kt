package vaida.dryzaite.foodmood.database

import androidx.room.Database
import androidx.room.RoomDatabase
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.model.RemoteKeys
import vaida.dryzaite.foodmood.network.ExternalRecipe

@Database (entities = [RecipeEntry::class, ExternalRecipe::class, RemoteKeys::class], version = 10, exportSchema = false)
abstract class RecipeDatabase: RoomDatabase() {

    abstract fun recipeDao(): RecipeDao
    abstract fun externalRecipesDao(): ExternalRecipesDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}

