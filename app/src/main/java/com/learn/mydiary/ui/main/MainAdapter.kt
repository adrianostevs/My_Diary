package com.learn.mydiary.ui.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.learn.mydiary.databinding.ItemListBinding
import com.learn.mydiary.domain.model.Story
import com.learn.mydiary.ui.detail.DetailActivity

class MainAdapter(
    diffUtil: DiffUtil.ItemCallback<Story>,
) : PagingDataAdapter<Story, MainAdapter.MainViewHolder>(diffUtil) {

    inner class MainViewHolder(private val binding: ItemListBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun binding(data: Story) {
            binding.apply {
                mtvTitle.text = data.name
                mtvDescription.text = data.description
                aivPicture.load(data.photoUrl)
                mcvContainer.setOnClickListener {
                    val intent = Intent(mcvContainer.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.INTENT_DATA, data)

                    val optionsCompat: ActivityOptionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        mcvContainer.context as Activity,
                        Pair(mtvTitle, "name"),
                        Pair(mtvDescription, "description"),
                        Pair(aivPicture, "picture")
                    )
                    mcvContainer.context.startActivity(intent, optionsCompat.toBundle())
                }
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

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Story>() {
            override fun areItemsTheSame(
                oldItem: Story,
                newItem: Story
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Story,
                newItem: Story
            ): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}