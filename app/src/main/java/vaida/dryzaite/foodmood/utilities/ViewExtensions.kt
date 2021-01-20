package vaida.dryzaite.foodmood.utilities

import android.content.Context
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import vaida.dryzaite.foodmood.R

fun Window.setStatusBar(context: Context, gradientStatusBar: Boolean = false) {

    this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    this.statusBarColor = ContextCompat.getColor(context, android.R.color.transparent)
    if (gradientStatusBar) {
        this.setBackgroundDrawableResource(R.drawable.gradient_toolbar)
    } else {
        this.statusBarColor = ContextCompat.getColor(context, R.color.colorPrimary)
    }
}
