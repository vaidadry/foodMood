package vaida.dryzaite.foodmood.model

//import android.content.Context
//import android.util.Log
//import com.google.gson.Gson
//import com.google.gson.reflect.TypeToken
//import java.io.IOException
//
//object RecipeStore {
//
//    private val TAG = "RECIPE_BOOK"
//    private lateinit var recipes: List<RecipeEntry>
//
//
//    // converting json to list
//    fun loadRecipes (context: Context) {
//        val gson = Gson()
//        val json = loadJSONFromAsset("recipe_DB.json", context)
//        val listType = object : TypeToken<List<RecipeEntry>>() {}.type
//        recipes = gson.fromJson(json, listType)
//        Log.v(TAG, "Found ${recipes.size} recipe entries \n $recipes")
//    }
//
//    fun getRecipes() = recipes
//
//    fun getRecipeEntryById(id: String) = recipes.firstOrNull { it.id == id }
//
//    // loading data from json file
//    private fun loadJSONFromAsset(filename: String, context: Context): String? {
//        var json: String? = null
//        try {
//            val inputStream = context.assets.open(filename)
//            val size = inputStream.available()
//            val buffer = ByteArray(size)
//            inputStream.read(buffer)
//            inputStream.close()
//            json = String(buffer)
//        } catch (ex: IOException) {
//            Log.e(TAG, "Error reading from $filename", ex)
//        }
//        return json
//    }
//
//}

