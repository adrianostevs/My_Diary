package com.learn.mydiary.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.mydiary.base.BaseActivity
import com.learn.mydiary.data.remote.model.request.StoryRequest
import com.learn.mydiary.data.remote.model.response.ResultResponse
import com.learn.mydiary.databinding.ActivityMainBinding
import com.learn.mydiary.domain.entity.StoryEntity
import com.learn.mydiary.domain.model.Story
import com.learn.mydiary.ui.bottomsheet.LogoutBottomSheet
import com.learn.mydiary.ui.dialog.AppDialog
import com.learn.mydiary.util.extension.showBottomSheet
import com.learn.mydiary.util.preferences.Preferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {


    private val mViewModel: MainViewModel by viewModels()

    private lateinit var storyAdapter: MainAdapter

    private val mLoadingDialog by lazy { AppDialog(supportFragmentManager) }

    @Inject
    lateinit var preferences: Preferences

    override fun onViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {

        storyAdapter = MainAdapter(object : DiffUtil.ItemCallback<Story>() {
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

        }, onItemSelected = {
            Toast.makeText(this@MainActivity, "OK", Toast.LENGTH_SHORT).show()
        })

        viewBinding.apply {
            rvList.apply {
                adapter = storyAdapter
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
            toolbar.aivProfile.setOnClickListener {
                showBottomSheet(LogoutBottomSheet())
            }
            toolbar.mtvToolbar.text = buildString {
                append("Hi, ")
                append(preferences.getValue(Preferences.NAME))
            }

            srlLoading.setOnRefreshListener { storyAdapter.refresh() }
        }

        mViewModel.getStory(StoryRequest())

        observeData()
    }

    private fun observeData() {
        lifecycleScope.launchWhenStarted {
            mViewModel.storyEvent.collectLatest {
                when (it) {
                    is StoryEvent.StoryLoading -> {

                    }
                    is StoryEvent.StoryFailed -> {
                        viewBinding.srlLoading.isRefreshing = false
                        Toast.makeText(this@MainActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is StoryEvent.StorySuccess -> {
                        viewBinding.srlLoading.isRefreshing = false
                        storyAdapter.submitData(it.data)
                    }
                }
            }
        }
    }
}