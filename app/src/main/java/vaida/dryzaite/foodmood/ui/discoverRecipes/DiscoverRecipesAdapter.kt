package vaida.dryzaite.foodmood.ui.discoverRecipes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import vaida.dryzaite.foodmood.databinding.GridListItemBinding
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.network.ExternalRecipe
import java.util.*
import kotlin.collections.ArrayList

class DiscoverRecipesAdapter : ListAdapter <ExternalRecipe, DiscoverRecipesAdapter.DiscoverRecipesViewHolder>(DiffCallback)
    //Filterable
 {
//    private lateinit var recipes: MutableList<ExternalRecipe>
//    var recipeFilterList = ArrayList<ExternalRecipe>()
//
//    init {
//        recipeFilterList = recipes as ArrayList<ExternalRecipe>
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverRecipesViewHolder {
        return DiscoverRecipesViewHolder(GridListItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: DiscoverRecipesViewHolder, position: Int) {
        val externalRecipe = getItem(position)
        holder.bind(externalRecipe)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ExternalRecipe>() {
        override fun areItemsTheSame(oldItem: ExternalRecipe, newItem: ExternalRecipe): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ExternalRecipe, newItem: ExternalRecipe): Boolean {
            return  oldItem.url == newItem.url
        }
    }

    // filter/search by title
//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val charSearch = constraint.toString()
//                recipeFilterList = if (charSearch.isEmpty()) {
//                    recipes as ArrayList<ExternalRecipe>
//                } else {
//                    val resultList = ArrayList<ExternalRecipe>()
//                    for (entry in recipes) {
//                        if (entry.title.toLowerCase(Locale.ROOT)
//                                .contains(charSearch.toLowerCase(Locale.ROOT))
//                        ) {
//                            resultList.add(entry)
//                        }
//                    }
//                    resultList
//                }
//                val filterResults = FilterResults()
//                filterResults.values = recipeFilterList
//                return filterResults
//            }
//            @Suppress ("UNCHECKED_CAST")
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                recipeFilterList = results?.values as ArrayList<ExternalRecipe>
//                notifyDataSetChanged()
//            }
//        }
//    }

    class DiscoverRecipesViewHolder(private var binding: GridListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(externalRecipe: ExternalRecipe) {
            binding.externalRecipe = externalRecipe
            binding.executePendingBindings()
        }
    }
}