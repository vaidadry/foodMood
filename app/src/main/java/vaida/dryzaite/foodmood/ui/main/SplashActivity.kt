package vaida.dryzaite.foodmood.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import vaida.dryzaite.foodmood.R

class SplashActivity : AppCompatActivity() {

    private val SPLASH_TIME_OUT: Long =  500

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.AppTheme)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        Handler().postDelayed({
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish()
        }, SPLASH_TIME_OUT)
    }
}
