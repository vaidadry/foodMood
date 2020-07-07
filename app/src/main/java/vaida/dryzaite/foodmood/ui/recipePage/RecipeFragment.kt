package vaida.dryzaite.foodmood.ui.recipePage

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import vaida.dryzaite.foodmood.R

class RecipeFragment : Fragment() {

    private lateinit var viewModel: RecipeViewModel
//    private lateinit var binding: FragmentRecipe

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        binding = RecipeFragment.inflate(inflator, container, false)
//        return binding.root
        return inflater.inflate(R.layout.recipe_fragment, container, false)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.top_nav_menu_item, menu)

        //setup clicks on icons in toolbar
        val shareItem = menu.findItem(R.id.menu_share_item)
        shareItem.actionView.setOnClickListener {
            menu.performIdentifierAction(
                shareItem.itemId,
                0
            )
        }
    }

//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item!!.itemId) {
//            R.id.menu_share_item -> shareRecipe()
//        }
//        return super.onOptionsItemSelected(item)
//    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(RecipeViewModel::class.java)
        // TODO: Use the ViewModel
    }

//    intent for sharing a recipe via other apps
//    private fun getShareIntent(): Intent {
//        val args = RecipeFragmentArgs.fromBundle(requireArguments()) // or any other way to get info from db
//        val shareIntent = Intent(Intent.ACTION_SEND)
//        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT,
//            getString(R.string.share_message, args.title, args.meal, args.url))
//        return shareIntent
//    }
//
//    private fun shareRecipe() {
//        startActivity(getShareIntent())
//    }




}