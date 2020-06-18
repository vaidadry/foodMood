package vaida.dryzaite.foodmood.model

import android.content.Context
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object Favorites {


        private val KEY_FAVORITES = "KEY_FAVORITES"
        private val gson = Gson()

        private var favorites: MutableList<String>? = null

        fun isFavorite(recipe: RecipeEntry, context: Context): Boolean {
            return getFavorites(context)?.contains(recipe.id) == true
        }

        fun addFavorite(recipe: RecipeEntry, context: Context) {
            val favorites = getFavorites(context)
            favorites?.let {
                recipe.isFavorite = true
                favorites.add(recipe.id)
                saveFavorites(KEY_FAVORITES, favorites, context)
            }
        }

        fun removeFavorite(recipe: RecipeEntry, context: Context) {
            val favorites = getFavorites(context)
            favorites?.let {
                recipe.isFavorite = false
                favorites.remove(recipe.id)
                saveFavorites(KEY_FAVORITES, favorites, context)
            }
        }


        fun getFavorites(context: Context): MutableList<String>? {
            if (favorites == null) {
                val json = sharedPrefs(context).getString(KEY_FAVORITES, "")
                val type = object : TypeToken<MutableList<Int>>() {}.type
                favorites = gson.fromJson<MutableList<String>>(json, type) ?: return mutableListOf()
            }
            return favorites
        }

        //saves arrangement changes in favorites screen
        fun saveFavorites(list: List<String>, context: Context) {
            saveFavorites(KEY_FAVORITES, list, context)
        }

        private fun saveFavorites(key: String, list: List<String>, context: Context) {
            val json = gson.toJson(list)
            sharedPrefs(context).edit().putString(key, json).apply()
        }

        private fun sharedPrefs(context: Context) = PreferenceManager.getDefaultSharedPreferences(context)



}