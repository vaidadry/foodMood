package vaida.dryzaite.foodmood.network

import com.squareup.moshi.Json

data class Response (
    @Json(name = "title") val providerTitle: String,
    val version: Float,
    val href: String,
    val results: List<ExternalRecipe> //kolkas rodo visada null
)

data class ExternalRecipe (
        val title: String,
        @Json(name = "href") val url: String,
        val ingredients: String,
        val thumbnail: String
    )


