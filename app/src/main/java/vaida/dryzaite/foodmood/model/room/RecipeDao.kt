package vaida.dryzaite.foodmood.model.room

import androidx.lifecycle.LiveData
import androidx.room.*
import vaida.dryzaite.foodmood.model.RecipeEntry

//creating interface for DAO
@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(recipe: RecipeEntry)

    @Update
   fun updateRecipe(recipe: RecipeEntry)

    @Delete
    fun deleteRecipe(recipe: RecipeEntry)

    //live data so that room update it automatically
    @Query("SELECT * from recipe_table ORDER BY title ASC")
    fun getAllRecipes(): LiveData<List<RecipeEntry>>

    //Selects and returns the recipe with given id(live data)
    @Query("SELECT * from recipe_table WHERE id = :key")
    fun getRecipeWithId(key: String): LiveData<RecipeEntry>

    //Select Favorites
    @Query("SELECT * from recipe_table WHERE is_favorite = 1 ORDER BY title ASC")
    fun getFavorites(): LiveData<List<RecipeEntry>>

    //Filter by meal and/or fish/veg
    @Query("SELECT * from recipe_table WHERE type_meal = :meal")
    fun getFilteredRecipes(meal: Int): LiveData<List<RecipeEntry>>

}