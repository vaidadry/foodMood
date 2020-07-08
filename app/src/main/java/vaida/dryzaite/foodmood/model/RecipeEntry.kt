package vaida.dryzaite.foodmood.model

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import vaida.dryzaite.foodmood.utilities.convertNumericMealTypeToString
import java.util.*


// creating entity with db table info, setting primary key to ID, which is Unique
@Entity(tableName = "recipe_table" )
data class RecipeEntry(
    @PrimaryKey @NonNull
    var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo (name = "title")
    val title: String,
    @ColumnInfo (name = "type_veggie")
    val veggie: Boolean,
    @ColumnInfo (name = "type_fish")
    val fish: Boolean,
    @ColumnInfo (name = "type_meal")
    val meal: Int,
    @ColumnInfo (name = "recipe_url")
    val recipe: String,
    @ColumnInfo (name = "is_favorite")
    var isFavorite: Boolean = false
)

