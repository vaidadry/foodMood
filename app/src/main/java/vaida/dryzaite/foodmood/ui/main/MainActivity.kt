package vaida.dryzaite.foodmood.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val bottomNav: BottomNavigationView = findViewById(R.id.bottom_nav)

        val navController = findNavController(R.id.nav_host_fragment_container)
        bottomNav.setupWithNavController(navController)

        mainViewModel.bottomNavigationVisibility.observe(this, Observer { navVisibility ->
            bottomNav.visibility = navVisibility
        })

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.homeFragment -> mainViewModel.showBottomNav()
                R.id.recipeListFragment -> mainViewModel.showBottomNav()
                else -> mainViewModel.hideBottomNav()
            }
        }
    }


}
