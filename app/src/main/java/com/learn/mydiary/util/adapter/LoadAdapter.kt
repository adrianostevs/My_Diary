package com.learn.mydiary.util.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.learn.mydiary.base.AppLoadingState
import com.learn.mydiary.base.AppRecyclerViewHolder
import com.learn.mydiary.databinding.AdapterLoadingBinding

class LoadAdapter: AppLoadingState<AdapterLoadingBinding, LoadAdapter.LoadStateViewHolder>(){
    override fun onViewBinding(parent: ViewGroup)= AdapterLoadingBinding.inflate(LayoutInflater.from(parent.context), parent, false)

    inner class LoadStateViewHolder(view: View) : AppRecyclerViewHolder<LoadState>(view){
        override fun bindItem(item: LoadState, position: Int) {
            viewBinding.lavProgress.isVisible = item is LoadState.Loading
            viewBinding.mtvError.isVisible = item is LoadState.Error
        }
    }

    override fun onViewHolder(view: View)= LoadStateViewHolder(view)

}