package vaida.dryzaite.foodmood.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val SPLASH_TIME_OUT: Long =  500

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish()
        }, SPLASH_TIME_OUT)
    }
}
