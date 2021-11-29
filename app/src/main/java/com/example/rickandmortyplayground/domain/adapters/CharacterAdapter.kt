package com.example.rickandmortyplayground.domain.adapters

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.rickandmortyplayground.R
import com.example.rickandmortyplayground.databinding.ItemCharacterBinding
import com.example.rickandmortyplayground.domain.models.Result


class CharacterAdapter : RecyclerView.Adapter<CharacterAdapter.CharacterViewHolder>() {

    private var binding: ItemCharacterBinding? = null

    inner class CharacterViewHolder(itemBinding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallback = object : DiffUtil.ItemCallback<Result>() {
        override fun areItemsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Result, newItem: Result): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        binding = ItemCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(requireNotNull(binding))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = differ.currentList[position]
        holder.itemView.apply {
            binding?.characterName?.text = character.name
            binding?.lastKnownLocation?.text = character.location?.name
            binding?.firstSeenIn?.text = character.origin?.name
            binding?.let {
                binding?.characterImage?.let { characterImage ->
                    Glide.with(it.root)
                        .load(character.image)
                        .apply(RequestOptions.centerCropTransform())
                        .into(characterImage)
                }
            }
            binding?.characterSpeciesAndStatus?.text =
                "${character.status} - ${character.species}"

            if (character.status?.contains("Dead") == true) {
                binding?.colorIndicator?.setImageResource(R.drawable.ic_circle_red)
            } else if (character.status?.contains("Alive") == true) {
                binding?.colorIndicator?.setImageResource(R.drawable.ic_circle_green)
            } else binding?.colorIndicator?.setImageResource(R.drawable.ic_circle_grey)

            setOnClickListener {
                onItemClickListener?.let { it(character) }
                Log.d("TAG", "${character.id}")
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    private var onItemClickListener: ((Result) -> Unit)? = null

    fun setOnItemClickListener(listener: (Result) -> Unit) {
        onItemClickListener = listener

    }


}