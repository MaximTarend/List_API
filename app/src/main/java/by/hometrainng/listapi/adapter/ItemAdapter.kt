package by.hometrainng.listapi.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import by.hometrainng.listapi.databinding.ItemBinding
import by.hometrainng.listapi.model.Character

class ItemAdapter(
    context: Context
): ListAdapter<Character, CharacterViewHolder>(DIFF_UTIL) {

    private val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return CharacterViewHolder(
            binding = ItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    companion object {
        private val  DIFF_UTIL = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }
}

class CharacterViewHolder(
    private val binding: ItemBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind (item: Character) {
        binding.itemText.text = item.login
    }

}

