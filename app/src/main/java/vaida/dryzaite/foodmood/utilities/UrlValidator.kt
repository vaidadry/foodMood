package vaida.dryzaite.foodmood.utilities

import android.util.Patterns

fun String.isValidUrl(): Boolean = Patterns.WEB_URL.matcher(this).matches()
