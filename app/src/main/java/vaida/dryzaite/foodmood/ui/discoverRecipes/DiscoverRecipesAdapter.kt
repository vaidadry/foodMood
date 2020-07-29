package vaida.dryzaite.foodmood.ui.discoverRecipes

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import vaida.dryzaite.foodmood.databinding.GridListItemBinding
import vaida.dryzaite.foodmood.model.RecipeEntry
import vaida.dryzaite.foodmood.network.ExternalRecipe
import vaida.dryzaite.foodmood.utilities.ExternalRecipesDiffCallback
import vaida.dryzaite.foodmood.utilities.RecipeDiffCallback
import java.util.*
import kotlin.collections.ArrayList

class DiscoverRecipesAdapter( private var recipes: MutableList<ExternalRecipe>, private val onClickListener: OnClickListener)
    : RecyclerView.Adapter <DiscoverRecipesAdapter.DiscoverRecipesViewHolder>(), Filterable
 {

    var recipeFilterList = ArrayList<ExternalRecipe>()

    init {
        recipeFilterList = recipes as ArrayList<ExternalRecipe>
        Timber.i("initial list created : $recipeFilterList")
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverRecipesViewHolder {
        return DiscoverRecipesViewHolder(GridListItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

     override fun getItemCount(): Int {
         return recipeFilterList.size
     }

// adding private listener to react on clicks
    override fun onBindViewHolder(holder: DiscoverRecipesViewHolder, position: Int) {
        val externalRecipe = recipeFilterList[position]
        holder.itemView.setOnClickListener {
            onClickListener.onClick(externalRecipe)
        }
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
     fun updateRecipes(recipes: List<ExternalRecipe>) {
         //implementing diff callback to calculate differences and send updates to adapter
         val diffCallback = ExternalRecipesDiffCallback(this.recipes, recipes)
         val diffResult = DiffUtil.calculateDiff(diffCallback)

         this.recipes.clear()
         this.recipes.addAll(recipes)

         diffResult.dispatchUpdatesTo(this)
     }

//     filter/search by title
    override fun getFilter(): Filter {
    Timber.i("filtering executed")
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                Timber.i("char search $charSearch")
                recipeFilterList = if (charSearch.isEmpty()) {
                    recipes as ArrayList<ExternalRecipe>
                } else {
                    val resultList = ArrayList<ExternalRecipe>()
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
                Timber.i(" recipe filter result: ${filterResults.values} ")
                return filterResults
            }
            @Suppress ("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults) {
                Timber.i("publishing filter results: constraint - $constraint ; results - ${results.values}")
                if (!recipeFilterList.isNullOrEmpty()) {
                    recipeFilterList = results.values as ArrayList<ExternalRecipe>
                    notifyDataSetChanged()
                }
            }
        }
    }

    class DiscoverRecipesViewHolder(private var binding: GridListItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(externalRecipe: ExternalRecipe) {
            binding.externalRecipe = externalRecipe
            binding.executePendingBindings()
        }
    }

     //click listener to react on clicks on RV items
     class OnClickListener(val clickListener: (externalRecipe:ExternalRecipe) -> Unit) {
         fun onClick(externalRecipe: ExternalRecipe) = clickListener(externalRecipe)
     }


 }