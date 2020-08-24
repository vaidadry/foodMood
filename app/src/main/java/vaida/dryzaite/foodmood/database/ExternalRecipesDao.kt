package vaida.dryzaite.foodmood.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import vaida.dryzaite.foodmood.network.ExternalRecipe

@Dao
interface ExternalRecipesDao {
    // DAO methods for Cached recipes

    @Query("SELECT * FROM external_recipes_table WHERE title LIKE :queryString OR ingredients LIKE :queryString")
    fun getExternalRecipes(queryString: String): PagingSource<Int, ExternalRecipe>

    @Insert(entity = ExternalRecipe::class, onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExternalRecipes(recipes: List<ExternalRecipe>)

    @Query("DELETE FROM external_recipes_table")
    suspend fun clearExternalRecipes()
}