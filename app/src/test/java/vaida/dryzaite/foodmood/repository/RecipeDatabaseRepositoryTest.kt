package vaida.dryzaite.foodmood.repository

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.network.ExternalRecipe

class RecipeDatabaseRepositoryTest: RecipeRepository {
    override suspend fun insertRecipe(recipe: RecipeEntry) {
        TODO("Not yet implemented")
    }

    override fun getAllRecipes(): LiveData<List<RecipeEntry>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRecipe(recipe: RecipeEntry) {
        TODO("Not yet implemented")
    }

    override suspend fun updateRecipe(recipe: RecipeEntry) {
        TODO("Not yet implemented")
    }

    override fun getRecipeWithId(key: String): LiveData<RecipeEntry> {
        TODO("Not yet implemented")
    }

    override fun getFavorites(): LiveData<List<RecipeEntry>> {
        TODO("Not yet implemented")
    }

    override fun getFilteredRecipes(meal: Int): LiveData<List<RecipeEntry>> {
        TODO("Not yet implemented")
    }

    override fun searchExternalRecipes(searchQuery: String): Flow<PagingData<ExternalRecipe>> {
        TODO("Not yet implemented")
    }

}