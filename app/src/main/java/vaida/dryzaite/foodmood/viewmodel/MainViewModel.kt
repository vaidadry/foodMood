package vaida.dryzaite.foodmood.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {

    private val _bottomNavigationVisibility = MutableLiveData<Int>()

    val bottomNavigationVisibility: LiveData<Int>
        get() = _bottomNavigationVisibility

    init {
        showBottomNav()
    }

    fun showBottomNav() {
        _bottomNavigationVisibility.postValue(View.VISIBLE)
    }

    fun hideBottomNav() {
        _bottomNavigationVisibility.postValue(View.GONE)
    }
}