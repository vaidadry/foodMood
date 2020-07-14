package vaida.dryzaite.foodmood.ui.recipeList

//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.CompoundButton
//import androidx.core.content.ContextCompat
//import androidx.lifecycle.ViewModelProvider
//import androidx.recyclerview.widget.RecyclerView
//import kotlinx.android.synthetic.main.list_item_recipe_view_holder.*
//import kotlinx.android.synthetic.main.list_item_recipe_view_holder.view.*
//import timber.log.Timber
//import vaida.dryzaite.foodmood.R
//import vaida.dryzaite.foodmood.databinding.ListItemRecipeViewHolderBinding
//import vaida.dryzaite.foodmood.model.RecipeEntry
//import vaida.dryzaite.foodmood.utilities.ItemSelectedListener
//// 1.4
//
//class RecipeListViewHolder(val binding: ListItemRecipeViewHolderBinding) : RecyclerView.ViewHolder(binding.root), ItemSelectedListener {
//
//    // binding data to list item layout
//    fun bind(recipe: RecipeEntry, clickListener: RecipeListOnClickListener) {
//        binding.dataclassRecipeEntry = recipe
//        binding.clickListener = clickListener
//
//
//        binding.favorite.setOnCheckedChangeListener { _, isChecked ->
//            recipe.isFavorite = isChecked
//            Timber.i("fun bind working -  click listener on line $recipe")
//        }
//            binding.favorite.isChecked = recipe.isFavorite
//            Timber.i("fun bind working - is checked  $recipe")
//
//        binding.executePendingBindings()
//
//
////        val status = adapterPosition
////           binding.recipeListViewModel?.updateRecipe(recipe)
////            Timber.i("fun bind update viewmodel $recipe")
////            val test = binding.recipeListViewModel?.getRecipeById(recipe.id)
////            Timber.i("fun bind get viewmodel ${test?.value}")
//
//
////        binding.favorite.isChecked = recipe.isFavorite
////        Timber.i("fun bind working - is checked  $recipe")
////        binding.recipeListViewModel?.updateRecipe(recipe)
////        Timber.i("fun bind update viewmodel $recipe")
//
//    }
//
////    //enable Favorite button
////    private fun setupFavoriteToggle(recipe: RecipeEntry) {
////        binding.favorite.setOnCheckedChangeListener { _, boolean ->
////            recipe.isFavorite = boolean
////        }
////        binding.favorite.isChecked = recipe.isFavorite
////    }
//
//
//    // adding on and off background colors on dragged item
//    override fun onItemSelected() {
//        itemView.list_item_container.setBackgroundColor(ContextCompat.getColor(itemView.context, R.color.colorPrimaryDark))
//    }
//
//    override fun onItemCleared() {
//        itemView.list_item_container.setBackgroundColor(0)
//    }
//
//    // for RW item layout binding
//    companion object {
//        fun from(parent: ViewGroup): RecipeListViewHolder {
//            val layoutInflater = LayoutInflater.from(parent.context)
//            val binding = ListItemRecipeViewHolderBinding.inflate(layoutInflater, parent, false)
//            return RecipeListViewHolder(binding)
//        }
//    }
//}








