package vaida.dryzaite.foodmood.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.util.UUID

// creating entity with db table info, setting primary key to ID, which is Unique
@Parcelize
@Entity(tableName = "recipe_table")
data class RecipeEntry(
    @PrimaryKey @NonNull
    var id: String = UUID.randomUUID().toString(),
    @ColumnInfo(name = "date")
    val date: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "type_veggie")
    val veggie: Boolean,
    @ColumnInfo(name = "type_fish")
    val fish: Boolean,
    @ColumnInfo(name = "type_meal")
    val meal: Int,
    @ColumnInfo(name = "recipe_url")
    val href: String,
    @ColumnInfo(name = "is_favorite")
    var isFavorite: Boolean = false,
    @ColumnInfo(name = "ingredients")
    var ingredients: String = "",
    @ColumnInfo(name = "thumbnail")
    var thumbnail: String? = null
) : Parcelable