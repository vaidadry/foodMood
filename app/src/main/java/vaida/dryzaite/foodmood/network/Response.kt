package vaida.dryzaite.foodmood.network

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

data class Response (
    @SerializedName("title") val providerTitle: String,
    val version: Float,
    @SerializedName("API_url") val href: String,
    val results: List<ExternalRecipe> = emptyList(),
    val nextPage: Int? = null
)

@Parcelize
@Entity(tableName = "cached_recipes_table" )
data class ExternalRecipe (
    val title: String,
    @PrimaryKey val href: String,
    val ingredients: String,
    val thumbnail: String
    ) : Parcelable


