package vaida.dryzaite.foodmood.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_discover_recipes.*
import kotlinx.android.synthetic.main.fragment_recipe_list.toolbar
import vaida.dryzaite.foodmood.FoodmoodApplication
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.ActivityMainBinding
import vaida.dryzaite.foodmood.di.MainComponent
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var viewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    lateinit var mainComponent: MainComponent

    override fun onCreate(savedInstanceState: Bundle?) {

        mainComponent = (application as FoodmoodApplication).appComponent.mainComponent().create()
        mainComponent.inject(this)

        super.onCreate(savedInstanceState)

        // Inflate with View binding to optimize space, as no databinding used
        binding = ActivityMainBinding.inflate(layoutInflater, container, false)
        setSupportActionBar(toolbar)

//        setting day/night mode as default
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment_container)
        binding.bottomNav.setupWithNavController(navController)
        // to ignore reloading on already open fragment
        binding.bottomNav.setOnNavigationItemReselectedListener {
        }

        viewModel.bottomNavigationVisibility.observe(this, { navVisibility ->
            binding.bottomNav.visibility = navVisibility
        })

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home_fragment_2 -> viewModel.showBottomNav()
                R.id.recipe_list_fragment -> viewModel.showBottomNav()
                R.id.discover_recipes_fragment -> viewModel.showBottomNav()
                else -> viewModel.hideBottomNav()
            }
        }
    }
}
