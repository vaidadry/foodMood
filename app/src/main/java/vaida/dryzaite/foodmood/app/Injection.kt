package vaida.dryzaite.foodmood.app

import vaida.dryzaite.foodmood.model.RecipeRepository
import vaida.dryzaite.foodmood.model.room.RoomRepository

object Injection {
    fun provideRecipeRepository(): RecipeRepository =
        RoomRepository()
}