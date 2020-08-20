package vaida.dryzaite.foodmood.ui.discoverRecipes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import timber.log.Timber
import vaida.dryzaite.foodmood.R
import vaida.dryzaite.foodmood.databinding.LoadStateItemBinding

//adapter for Load State
class RecipeLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<RecipeLoadStateAdapter.LoadStateViewHolder>() {
    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder.create(parent, retry)
    }

    class LoadStateViewHolder(
        private val binding: LoadStateItemBinding,
        retry: () -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.retryButton.setOnClickListener { retry.invoke() }
        }

        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                Timber.e( "Error: ${loadState.error.localizedMessage}")
                binding.errorMsg.setText(R.string.load_error_message)
            }
            binding.progressBar.isVisible = loadState is LoadState.Loading
            binding.retryButton.isVisible = loadState !is LoadState.Loading
            binding.errorMsg.isVisible = loadState !is LoadState.Loading
        }

        companion object {
            fun create(parent: ViewGroup, retry: () -> Unit): LoadStateViewHolder {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.load_state_item, parent, false)
                val binding = LoadStateItemBinding.bind(view)
                return LoadStateViewHolder(binding, retry)
            }
        }
    }
}