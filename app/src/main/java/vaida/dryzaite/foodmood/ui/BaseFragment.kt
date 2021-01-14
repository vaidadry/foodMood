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
import vaida.dryzaite.foodmood.BR

abstract class BaseFragment<VM : ViewModel, T : ViewDataBinding> : Fragment() {
    abstract val navigationSettings: NavigationSettings?
    abstract val layoutId: Int

    lateinit var binding: T
    lateinit var viewModel : VM


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(getViewModelClass())
        binding.setVariable(BR.viewModel, viewModel)
        setupUI()
        return view
    }

    abstract fun getViewModelClass() : Class<VM>
    abstract fun setupUI()
    }