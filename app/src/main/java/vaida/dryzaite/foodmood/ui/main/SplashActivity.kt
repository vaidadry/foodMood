package vaida.dryzaite.foodmood.ui.main

import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_discover_recipes.*
import vaida.dryzaite.foodmood.databinding.ActivitySplashBinding
import vaida.dryzaite.foodmood.utilities.SPLASH_TIME_OUT

@AndroidEntryPoint
class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        // todo:  do full screen style
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }

        super.onCreate(savedInstanceState)
        // Inflate with View binding to optimize space, as no data binding used
        binding = ActivitySplashBinding.inflate(layoutInflater, container, false)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT)
    }
}