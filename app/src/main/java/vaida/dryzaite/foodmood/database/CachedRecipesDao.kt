package vaida.dryzaite.foodmood.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vaida.dryzaite.foodmood.network.ExternalRecipe

@Dao
interface CachedRecipesDao {
    // DAO methods for Cached recipes

    @Query("SELECT * FROM cached_recipes_table WHERE title LIKE :queryString OR ingredients LIKE :queryString")
    fun getCachedRecipes(queryString: String): PagingSource<Int, ExternalRecipe>

    @Insert(entity = ExternalRecipe::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCachedRecipes(recipes: List<ExternalRecipe>)

    @Query("DELETE FROM cached_recipes_table")
    suspend fun clearCachedRecipes()
}