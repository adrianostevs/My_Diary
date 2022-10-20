package com.learn.mydiary.ui.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.learn.mydiary.databinding.ItemListBinding
import com.learn.mydiary.domain.entity.StoryEntity
import com.learn.mydiary.domain.model.Story

class MainAdapter(
    diffUtil: DiffUtil.ItemCallback<Story>,
    private val onItemSelected: (Story) -> Unit
) : PagingDataAdapter<Story, MainAdapter.MainViewHolder>(diffUtil) {

    inner class MainViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(data: Story) {
            binding.apply {
                mtvTitle.text = data.name
                mtvDescription.text = data.description
                aivPicture.load(data.photoUrl)
                mcvContainer.setOnClickListener { onItemSelected.invoke(data) }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            ItemListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        getItem(position)?.let { holder.binding(it) }
    }

}