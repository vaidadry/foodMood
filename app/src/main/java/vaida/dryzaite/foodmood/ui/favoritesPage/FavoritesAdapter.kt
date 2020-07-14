package vaida.dryzaite.foodmood.ui.favoritesPage

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.favorites_card_item.view.*
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.FavoritesCardItemBinding
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.utilities.ItemSelectedListener
import vaida.dryzaite.foodmood.utilities.ItemTouchHelperListener
import vaida.dryzaite.foodmood.utilities.RecipeDiffCallback
import java.util.*
import kotlin.collections.ArrayList

class FavoritesAdapter(
    private val recipes: MutableList<RecipeEntry>,
    private val clickListener: FavoritesOnClickListener,
    private val listener: FavoritesAdapterListener)
    : RecyclerView.Adapter<FavoritesAdapter.FavoritesViewHolder>(), ItemTouchHelperListener, Filterable {


    var scrollDirection = ScrollDirection.DOWN
    var recipeFilterList = ArrayList<RecipeEntry>()

    init {
        recipeFilterList = recipes as ArrayList<RecipeEntry>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = FavoritesCardItemBinding.inflate(layoutInflater, parent, false)
        return FavoritesViewHolder(binding)
    }

    override fun getItemCount() = recipeFilterList.size

    override fun onBindViewHolder(holder: FavoritesViewHolder, position: Int) {
        holder.bind(recipeFilterList[position], clickListener)
    }

    fun updateRecipes(recipes: List<RecipeEntry>) {
        //implementing diff callback to calculate differences and send updates to adapter
        val diffCallback = RecipeDiffCallback(this.recipes, recipes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.recipes.clear()
        this.recipes.addAll(recipes)

        diffResult.dispatchUpdatesTo(this)
    }

    override fun onItemDismiss(viewHolder: RecyclerView.ViewHolder, position: Int) {
    }


    // interface method implemented in adapter, to track position moved
    override fun onItemMove(recyclerView: RecyclerView, fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(recipes, i, i + 1)
                }
            }else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(recipes, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    // filter/search by title
    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                recipeFilterList = if (charSearch.isEmpty()) {
                    recipes as ArrayList<RecipeEntry>
                } else {
                    val resultList = ArrayList<RecipeEntry>()
                    for (entry in recipes) {
                        if (entry.title.toLowerCase(Locale.ROOT)
                                .contains(charSearch.toLowerCase(Locale.ROOT))
                        ) {
                            resultList.add(entry)
                        }
                    }
                    resultList
                }
                val filterResults = FilterResults()
                filterResults.values = recipeFilterList
                return filterResults
            }
            @Suppress ("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                recipeFilterList = results?.values as ArrayList<RecipeEntry>
                notifyDataSetChanged()
            }
        }
    }


    inner class FavoritesViewHolder(val binding: FavoritesCardItemBinding) : RecyclerView.ViewHolder(binding.root),
        ItemSelectedListener {

        // binding data to list item layout
        fun bind(recipe: RecipeEntry, clickListener: FavoritesOnClickListener) {
            binding.dataclassRecipeEntry = recipe
            binding.clickListener = clickListener

            // click listener to change DB and UI according to fav button clicks
            binding.favoriteIcon.isChecked = recipe.isFavorite
            binding.favoriteIcon.setOnCheckedChangeListener { _, _ ->
                if (binding.favoriteIcon.isShown) {
                    if (binding.favoriteIcon.isChecked) {
                        listener.addFavorites(recipe)
                    } else {
                        listener.removeFavorites(recipe)
                    }
                }
            }

            animateView(itemView)
            binding.executePendingBindings()
        }


        // adding on and off background colors on dragged item
        override fun onItemSelected() {
            itemView.recipe_card_container.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorPrimaryDark))
        }

        override fun onItemCleared() {
            itemView.recipe_card_container.setBackgroundColor(0)
        }

        private fun animateView(viewToAnimate: View) {
            if (viewToAnimate.animation == null) {
                val animId = if (scrollDirection == ScrollDirection.DOWN) R.anim.slide_from_bottom else R.anim.slide_from_top
                val animation = AnimationUtils.loadAnimation(viewToAnimate.context, animId )
                viewToAnimate.animation = animation
            }
        }
    }


    // to track scroll direction - for scroll animation
    enum class ScrollDirection {
        UP, DOWN
    }
    interface FavoritesAdapterListener {
        fun addFavorites(recipe: RecipeEntry)
        fun removeFavorites(recipe: RecipeEntry)
    }
}

//defining click listeners to respond to clicks on RW
open class FavoritesOnClickListener(val clickListener: (id: String) -> Unit) {
    fun onClick(recipe: RecipeEntry) = clickListener(recipe.id)
}



