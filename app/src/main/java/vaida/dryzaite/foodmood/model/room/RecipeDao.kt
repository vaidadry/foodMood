package vaida.dryzaite.foodmood.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import io.reactivex.Flowable
import vaida.dryzaite.foodmood.model.RecipeEntry


//creating interface for DAO
@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: RecipeEntry)

    @Delete
    fun deleteRecipe(recipe: RecipeEntry)

    @Query("SELECT * from recipe_table ORDER BY title ASC")
    fun getAllRecipes(): LiveData<List<RecipeEntry>>
//    fun getAllRecipes(): Flowable<List<RecipeEntry>>

    @Update
    fun updateRecipe(recipe: RecipeEntry)

}