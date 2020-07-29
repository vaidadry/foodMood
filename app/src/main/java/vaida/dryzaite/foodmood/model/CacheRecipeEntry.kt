package vaida.dryzaite.foodmood.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import vaida.dryzaite.foodmood.network.ExternalRecipe

// represents recipe entity in DB
@Entity(tableName = "cached_recipe_table" )
data class CacheRecipeEntry constructor(
    val title: String,
    @PrimaryKey val url: String,
    val ingredients: String,
    val thumbnail: String)


//extension function to convert database objects to domain objects
fun List<CacheRecipeEntry>.asDomainModel(): List<ExternalRecipe> {
    return map {
        ExternalRecipe(
            title = it.title,
            url = it.url,
            ingredients = it.ingredients,
            thumbnail = it.thumbnail)
    }
}
