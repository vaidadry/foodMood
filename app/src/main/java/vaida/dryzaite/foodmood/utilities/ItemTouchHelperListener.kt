package vaida.dryzaite.foodmood.utilities

import androidx.recyclerview.widget.RecyclerView

//to implement Swipes and Drags to up-down directions interactions
interface ItemTouchHelperListener {
    fun onItemMove(recyclerView: RecyclerView, fromPosition: Int, toPosition: Int): Boolean
}