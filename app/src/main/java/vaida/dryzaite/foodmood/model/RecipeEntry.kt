package vaida.dryzaite.foodmood.model

data class RecipeEntry(
    val id: Int,
    val date: String,
    val title: String,
    val comfortFood: Boolean,
    val fish: Boolean,
    val meal: String,
    val recipe: String) {

    val thumbnail: String
        get() = "drawable/ic_$meal"
}
