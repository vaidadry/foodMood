package vaida.dryzaite.foodmood.ui.recipeList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FragmentAddRecipeBinding
import vaida.dryzaite.foodmood.viewmodel.AddRecipeViewModel


class AddRecipeFragment : Fragment(){

    private lateinit var viewModel: AddRecipeViewModel

    //adding binding between view and viewModel
    private lateinit var binding: FragmentAddRecipeBinding


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentAddRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this).get(AddRecipeViewModel::class.java)
        binding.viewmodel = viewModel

        configureLiveDataObserver()
    }

//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        (activity as MainActivity).hideBottomNavigation()
//    }
//
//    override fun onDetach() {
//        (activity as MainActivity).showBottomNavigation()
//        super.onDetach()
//    }
//
//    override fun onResume() {
//        super.onResume()
//        (activity as MainActivity).hideBottomNavigation()
//    }


//    HELPERS for adding input data to ViewModel



//    since no click listener to save item, the observer send Success/error toast
    private fun configureLiveDataObserver() {
        viewModel.getSaveLiveData().observe(viewLifecycleOwner, Observer { saved ->
            saved?.let {
                if (saved) {
                Toast.makeText(context, getString(R.string.saved_successfully), Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_addRecipeFragment_to_recipeListFragment)
            } else {
                Toast.makeText(context, getString(R.string.error_saving_recipe), Toast.LENGTH_SHORT).show()
                }
            }
        })
    }



}
