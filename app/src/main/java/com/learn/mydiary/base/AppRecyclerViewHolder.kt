package com.learn.mydiary.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView

abstract class AppRecyclerViewHolder<M>(view: View) : RecyclerView.ViewHolder(view) {

    abstract fun bindItem(item: M, position: Int)

}