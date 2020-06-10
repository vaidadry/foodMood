package vaida.dryzaite.foodmood.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import vaida.dryzaite.foodmood.R


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupViews()
    }


    fun setupViews() {
        val navController = findNavController(R.id.nav_host_fragment_container)
        bottom_nav.setupWithNavController(navController)
    }

    fun showBottomNavigation() {
        bottom_nav.visibility = View.VISIBLE
    }

    fun hideBottomNavigation() {
        bottom_nav.visibility = View.GONE
    }



//
//    // pass data from one fragment to another
//    override fun passData(id: Int) {
//        val bundle = Bundle()
//        bundle.putInt("random_id", id)
//        showSuggestion(bundle)
//    }
//
//    private fun showSuggestion(bundle: Bundle) {
//        val transaction = this.supportFragmentManager.beginTransaction()
//        val suggestionFragment = SuggestionFragment()
//        suggestionFragment.arguments = bundle
//        transaction.setCustomAnimations(R.anim.slide_up_anim, 0, 0, R.anim.slide_down_anim)
//        transaction.replace(R.id.home_fragment, suggestionFragment)
//        transaction.addToBackStack(null)
//        transaction.commit()
//    }
}
