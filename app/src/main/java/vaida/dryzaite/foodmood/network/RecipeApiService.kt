package vaida.dryzaite.foodmood.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


interface RecipeApiService {
    @GET("api")
    fun getRecipesAsync(
        @Query("q") searchQuery: String? = "",
        @Query("p") page: Int = 1
    ) : Call<Response>

    companion object {
        private const val BASE_URL = "http://www.recipepuppy.com"

        fun create(): RecipeApiService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(RecipeApiService::class.java)
        }
    }
}



