package vaida.dryzaite.foodmood.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_home_2.*
import kotlinx.android.synthetic.main.fragment_recipe_list.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(toolbar)

//        setting day/night mode as default
//        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)


        val viewModelFactory = MainViewModelFactory()
        viewModel = ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)

        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val navController = findNavController(R.id.nav_host_fragment_container)
        bottom_nav.setupWithNavController(navController)
        // to ignore reloading on already open fragment
        bottom_nav.setOnNavigationItemReselectedListener {
        }

        viewModel.bottomNavigationVisibility.observe(this, Observer { navVisibility ->
            bottom_nav.visibility = navVisibility
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
