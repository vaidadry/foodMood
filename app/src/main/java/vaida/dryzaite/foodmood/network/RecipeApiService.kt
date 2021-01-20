package vaida.dryzaite.foodmood.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface RecipeApiService {
    @GET("api")
    fun getRecipesAsync(
        @Query("q") searchQuery: String? = "",
        @Query("p") page: Int = 1
    ): Call<Response>

    @GET("api")
    fun getRecipesByIngredientAsync(
        @Query("i") searchQuery: String? = "",
        @Query("p") page: Int = 1
    ): Call<Response>
}