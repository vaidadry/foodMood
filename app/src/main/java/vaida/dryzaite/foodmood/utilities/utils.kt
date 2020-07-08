package vaida.dryzaite.foodmood.utilities

import android.content.res.Resources
import vaida.dryzaite.foodmood.R


// as DB uses Integers to divide items into categories, here is the conversion to Strings
fun convertNumericMealTypeToString(mealSelection: Int, resources: Resources): String {

    var mealString = resources.getString(R.string.dinner)
    when (mealSelection) {
        1 -> mealString = resources.getString(R.string.breakfast)
        2 -> mealString = resources.getString(R.string.brunch)
        3 -> mealString = resources.getString(R.string.lunch)
        4 -> mealString = resources.getString(R.string.dinner)
        5 -> mealString = resources.getString(R.string.sweets)
    }
    return mealString
}