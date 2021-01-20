package vaida.dryzaite.foodmood.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.ActivityMainBinding
import vaida.dryzaite.foodmood.ui.MainActivityFragmentFactory
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var fragmentFactory: MainActivityFragmentFactory

    private val viewModel: MainViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.AppTheme)
        supportFragmentManager.fragmentFactory = fragmentFactory

        // Inflate with View binding to optimize space, as no data binding used
        binding = ActivityMainBinding.inflate(layoutInflater, activity_main_layout, false)

        // setting day/night mode as default
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        setContentView(binding.root)
        val navController = findNavController(R.id.nav_host_fragment_container)
        binding.bottomNav.setupWithNavController(navController)

        // to ignore reloading on already open fragment
        binding.bottomNav.setOnNavigationItemReselectedListener {}

        viewModel.bottomNavigationVisibility.observe(this, { navVisibility ->
            binding.bottomNav.visibility = navVisibility
        })

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.home_fragment_2 -> viewModel.showBottomNav()
                R.id.recipe_list_fragment -> viewModel.showBottomNav()
                R.id.discover_recipes_fragment -> viewModel.showBottomNav()
                R.id.discoverRecipesIngredientFragment -> viewModel.showBottomNav()
                else -> viewModel.hideBottomNav()
            }
        }
    }
}
