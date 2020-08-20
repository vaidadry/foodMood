package vaida.dryzaite.foodmood.network

import android.os.Parcelable
import com.squareup.moshi.Json
import kotlinx.android.parcel.Parcelize
import vaida.dryzaite.foodmood.model.CacheRecipeEntry

data class Response (
    @Json(name = "title") val providerTitle: String,
    val version: Float,
    @Json(name = "API url") val href: String,
    val results: List<ExternalRecipe> = emptyList(),
    val nextPage: Int? = null
)

@Parcelize
data class ExternalRecipe (
    val title: String,
    val href: String,
    val ingredients: String,
    val thumbnail: String
    ) : Parcelable

// extension function to convert network results to DB objects
fun Response.asDatabaseModel(): List<CacheRecipeEntry> {
    return results.map {
        CacheRecipeEntry(
            title = it.title,
            url = it.href,
            ingredients = it.ingredients,
            thumbnail = it.thumbnail
        )
    }
}


