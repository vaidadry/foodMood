package vaida.dryzaite.foodmood.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import vaida.dryzaite.foodmood.model.CacheRecipeEntry

data class Response (
    @Json(name = "title") val providerTitle: String,
    val version: Float,
    val href: String,
    val results: List<ExternalRecipe>
)

@Parcelize
data class ExternalRecipe (
        val title: String,
        @Json(name = "href") val url: String,
        val ingredients: String,
        val thumbnail: String
    ) : Parcelable


// extension function to convert network results to DB objects
fun Response.asDatabaseModel(): List<CacheRecipeEntry> {
    return results.map {
        CacheRecipeEntry(
            title = it.title,
            url = it.url,
            ingredients = it.ingredients,
            thumbnail = it.thumbnail
        )
    }
}


