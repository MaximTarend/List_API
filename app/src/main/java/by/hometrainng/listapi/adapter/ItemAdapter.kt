package by.hometrainng.listapi.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.hometrainng.listapi.databinding.ItemBinding
import by.hometrainng.listapi.databinding.LoadingItemBinding
import by.hometrainng.listapi.model.ListElement


class ItemAdapter(
    context: Context
): ListAdapter<ListElement, RecyclerView.ViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun getItemViewType(position: Int): Int {
        return when(getItem(position)) {
            is ListElement.Character -> TYPE_CHARACTER
            ListElement.Loading -> TYPE_LOADING
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
                    TYPE_CHARACTER -> {
                        CharacterViewHolder(binding = ItemBinding.inflate(layoutInflater, parent, false))
                    }
                    TYPE_LOADING -> {
                        LoadingViewHolder(binding = LoadingItemBinding.inflate(layoutInflater, parent, false))
                    }
            else -> {
                error("Unknown View Type")}
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder !is CharacterViewHolder) return
        val item = getItem(position) as? ListElement.Character ?: return
        holder.bind(item)
    }


    companion object {

        private const val TYPE_CHARACTER = 0
        private const val TYPE_LOADING = 1

        private val  DIFF_UTIL = object : DiffUtil.ItemCallback<ListElement>() {
            override fun areItemsTheSame(oldItem: ListElement, newItem: ListElement): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: ListElement, newItem: ListElement): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class CharacterViewHolder(
    private val binding: ItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind (item: ListElement.Character) {
        binding.itemText.text = item.login
    }
}

class LoadingViewHolder(
    binding: LoadingItemBinding
) : RecyclerView.ViewHolder(binding.root)

