package vaida.dryzaite.foodmood.ui.discoverRecipePage

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_discover_recipe_detail.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentDiscoverRecipeDetailBinding
import vaida.dryzaite.foodmood.ui.main.MainActivity
import javax.inject.Inject

@AndroidEntryPoint
class DiscoverRecipeDetailFragment : Fragment(), Toolbar.OnMenuItemClickListener {

    private val viewModel: DiscoverRecipeDetailViewModel by viewModels()
    private lateinit var binding: FragmentDiscoverRecipeDetailBinding

    private val args by navArgs<DiscoverRecipeDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_discover_recipe_detail, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.setRecipe(args.selectedRecipe)

        return binding.root
    }


    override fun onMenuItemClick(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.menu_share_item -> {
                shareRecipe()
                true
            }
            else -> false
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        toolbar_discover_recipe_detail.setOnMenuItemClickListener(this)
        //observer handling clicks on URL field
        viewModel.navigateToUrl.observe(viewLifecycleOwner, Observer {
            it?.let {
                redirectToRecipeUrl(it)
                viewModel.onButtonClicked()
            }
        })

        //observer to navigate to Add recipe fragment, passing info via Safe Args
        viewModel.navigateToAddRecipe.observe(viewLifecycleOwner, Observer {
            if (it != null) {
                    this.findNavController().navigate(
                        DiscoverRecipeDetailFragmentDirections.actionDiscoverRecipeDetailFragmentToAddRecipeFragment2(it)
                    )
                    viewModel.onClickAddFragmentComplete()
                }
            })
    }


//    intent for sharing a recipe via other apps - "hardcoded text- as we dont have meal info"
    private fun getShareIntent(): Intent {
        val recipe = viewModel.selectedRecipe.value!!
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain").putExtra(Intent.EXTRA_TEXT,
            getString(R.string.share_message, recipe.title, "NomNom", recipe.href))
        return shareIntent
    }

    private fun shareRecipe() {
        startActivity(getShareIntent())
    }


    //  linking to external URL
    private fun redirectToRecipeUrl(url: String) {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        view?.context?.startActivity(intent)
    }


}