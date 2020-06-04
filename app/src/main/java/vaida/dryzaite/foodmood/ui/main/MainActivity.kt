package vaida.dryzaite.foodmood.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

import kotlinx.android.synthetic.main.activity_main.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.ui.homePage.SuggestionFragment
import vaida.dryzaite.foodmood.ui.homePage.Communicator
import vaida.dryzaite.foodmood.ui.homePage.HomeFragment
import vaida.dryzaite.foodmood.ui.recipeList.RecipeListFragment

class MainActivity : AppCompatActivity(), Communicator {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val homeFragment = HomeFragment()
        val recipeListFragment = RecipeListFragment()

        makeCurrentFragment(homeFragment)
        setupNavigation(homeFragment, recipeListFragment)
    }

    private fun setupNavigation(
        homeFragment: HomeFragment,
        recipeListFragment: RecipeListFragment
    ) {
        nav_view.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.navigation_mainActivity -> makeCurrentFragment(homeFragment)
                R.id.navigation_recipeList -> makeCurrentFragment(recipeListFragment)
            }
            true
        }
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fl_wrapper, fragment)
            commit()
        }

    // pass data from one fragment to another
    override fun passData(id: Int) {
        val bundle = Bundle()
        bundle.putInt("random_id", id)
        showSuggestion(bundle)
    }

    private fun showSuggestion(bundle: Bundle) {
        val transaction = this.supportFragmentManager.beginTransaction()
        val suggestionFragment = SuggestionFragment()
        suggestionFragment.arguments = bundle
        transaction.setCustomAnimations(R.anim.slide_up_anim, 0, 0, R.anim.slide_down_anim)
        transaction.replace(R.id.home_fragment, suggestionFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}
