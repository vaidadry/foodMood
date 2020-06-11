package vaida.dryzaite.foodmood.model

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


// creating entity with db table info, setting primary key to ID, which is Unique
@Entity(tableName = "recipe_table" )
data class RecipeEntry(
    @PrimaryKey @NonNull val id: String = UUID.randomUUID().toString(),
    val date: String,
    val title: String,
    val comfortFood: Boolean,
    val fish: Boolean,
    val meal: String,
    val recipe: String) {

//    val thumbnail: String
//        get() = "drawable/ic_$meal"

    fun thumbnail(meal: String): String {
        return "drawable/ic_$meal"
    }
}
