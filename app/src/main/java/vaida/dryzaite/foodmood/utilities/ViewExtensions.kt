package vaida.dryzaite.foodmood.utilities

import android.content.Context
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import vaida.dryzaite.foodmood.R

fun Window.setStatusBar(context: Context, gradientStatusBar: Boolean = false) {
    this.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    this.statusBarColor = ContextCompat.getColor(context, android.R.color.transparent)
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        this.setDecorFitsSystemWindows(false)
    } else {
        this.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR and View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
    }
    if (gradientStatusBar) {
        this.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.gradient_toolbar))
    } else {
        this.statusBarColor = ContextCompat.getColor(context, R.color.colorPrimary)
    }

}
