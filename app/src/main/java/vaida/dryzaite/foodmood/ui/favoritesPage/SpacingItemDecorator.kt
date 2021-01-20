package vaida.dryzaite.foodmood.ui.favoritesPage

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView

// recyclerview subclass of spacing decorator, to equalize spacing
class SpacingItemDecorator(private val spanCount: Int, private val spacing: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        val position = parent.getChildAdapterPosition(view)

        outRect.top = spacing / 2
        outRect.bottom = spacing / 2
        outRect.left = spacing / 2
        outRect.right = spacing / 2

        // adjust top edge
        if (position < spanCount) {
            outRect.top = spacing
        }

        // adjust left edge
        if (position % spanCount == 0) {
            outRect.left = spacing
        }

        // adjust right side
        if ((position + 1) % spanCount == 0) {
            outRect.right = spacing
        }
    }
}
