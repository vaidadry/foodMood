package vaida.dryzaite.foodmood.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.toolbar.view.*
import vaida.dryzaite.foodmood.BR
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.utilities.setStatusBar

abstract class BaseFragment<VM : ViewModel, T : ViewDataBinding> : Fragment() {
    abstract val navigationSettings: NavigationSettings?
    abstract val layoutId: Int

    lateinit var binding: T
    lateinit var viewModel : VM
    private lateinit var mainView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val navSettings = navigationSettings

        if (navSettings == null) {
            binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
            mainView = binding.root

            requireActivity().window.setStatusBar(requireContext())

        } else {
            val baseFragment = inflater.inflate(R.layout.toolbar, container, false) as ViewGroup
            binding = DataBindingUtil.inflate(inflater, layoutId, baseFragment, false)
            baseFragment.toolbarFragmentContent.removeAllViews()
            baseFragment.toolbarFragmentContent.addView(binding.root)
            mainView = baseFragment

            requireActivity().window.setStatusBar(requireContext(), true)

            navSettings.title?.let {
                baseFragment.toolbar.title = it
            }
        }

        viewModel = ViewModelProvider(this).get(getViewModelClass())
        binding.setVariable(BR.viewModel, viewModel)
        setupUI()

        return mainView
    }

    abstract fun getViewModelClass() : Class<VM>
    abstract fun setupUI()
}