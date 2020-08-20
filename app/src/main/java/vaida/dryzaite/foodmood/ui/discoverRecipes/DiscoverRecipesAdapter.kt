package vaida.dryzaite.foodmood.ui.discoverRecipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import vaida.dryzaite.foodmood.databinding.GridListItemBinding
import vaida.dryzaite.foodmood.network.ExternalRecipe

// PagingDataAdapter used to implement Paging Lib in RecyclerView
class DiscoverRecipesAdapter (private val onClickListener: OnClickListener)
    : PagingDataAdapter <ExternalRecipe, DiscoverRecipesAdapter.DiscoverRecipesViewHolder>(
    diffUtil) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DiscoverRecipesViewHolder {
        return DiscoverRecipesViewHolder(GridListItemBinding.inflate(
            LayoutInflater.from(parent.context)))
    }

    // adding private listener to react on clicks
    override fun onBindViewHolder(holder: DiscoverRecipesViewHolder, position: Int) {
        val externalRecipe = getItem(position)
        if (externalRecipe != null) {
            holder.itemView.setOnClickListener {
                onClickListener.onClick(externalRecipe)
            }
            holder.bind(externalRecipe)
        }
    }

     companion object {
         private val diffUtil = object : DiffUtil.ItemCallback<ExternalRecipe>() {
             override fun areItemsTheSame(oldItem: ExternalRecipe, newItem: ExternalRecipe): Boolean =
                 oldItem == newItem

             override fun areContentsTheSame(oldItem: ExternalRecipe, newItem: ExternalRecipe): Boolean =
                 oldItem.href == newItem.href
         }
     }

    class DiscoverRecipesViewHolder(private var binding: GridListItemBinding): RecyclerView.ViewHolder(binding.root) {

        //binding via DataBinding Lib
        fun bind(externalRecipe: ExternalRecipe?) {
                binding.externalRecipe = externalRecipe
                binding.executePendingBindings()
        }
    }

     //click listener to react on clicks on RV items
     class OnClickListener(val clickListener: (externalRecipe:ExternalRecipe) -> Unit) {
         fun onClick(externalRecipe: ExternalRecipe) = clickListener(externalRecipe)
     }
}