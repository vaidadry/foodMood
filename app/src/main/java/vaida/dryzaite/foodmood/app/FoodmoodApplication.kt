package vaida.dryzaite.foodmood.app

import android.app.Application
import vaida.dryzaite.foodmood.model.RecipeBook

class FoodmoodApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        RecipeBook.loadRecipes(this)
    }
}
