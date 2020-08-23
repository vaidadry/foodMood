package vaida.dryzaite.foodmood.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_keys")
data class RemoteKeys(
    @PrimaryKey
    val recipeHref: String,
    val prevKey: Int?,
    val nextKey: Int?
)