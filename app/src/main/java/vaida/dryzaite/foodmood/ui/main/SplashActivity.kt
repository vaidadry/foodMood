package vaida.dryzaite.foodmood.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_discover_recipes.*
import vaida.dryzaite.foodmood.FoodmoodApplication
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.ActivitySplashBinding

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    private val SPLASH_TIME_OUT: Long =  500

    override fun onCreate(savedInstanceState: Bundle?) {

        setTheme(R.style.AppTheme)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        actionBar?.hide()

        super.onCreate(savedInstanceState)
        // Inflate with View binding to optimize space, as no data binding used
        binding = ActivitySplashBinding.inflate(layoutInflater, container, false)
        setContentView(binding.root)

        Handler().postDelayed({
            startActivity(
                Intent(this, MainActivity::class.java)
            )
            finish()
        }, SPLASH_TIME_OUT)
    }
}
