package com.learn.mydiary.ui.main

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.learn.mydiary.base.BaseActivity
import com.learn.mydiary.databinding.ActivityMainBinding
import com.learn.mydiary.domain.model.Story
import com.learn.mydiary.ui.addstory.AddStoryActivity
import com.learn.mydiary.ui.auth.login.LoginActivity
import com.learn.mydiary.ui.maps.MapsActivity
import com.learn.mydiary.util.adapter.LoadAdapter
import com.learn.mydiary.util.preferences.Preferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity: BaseActivity<ActivityMainBinding>() {

    @Inject
    lateinit var preferences: Preferences

    private val mViewModel: MainViewModel by viewModels()

    private lateinit var storyAdapter: MainAdapter

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

        })
        viewBinding.apply {
            rvList.apply {
                adapter = storyAdapter.withLoadStateHeaderAndFooter(LoadAdapter(), LoadAdapter())
                layoutManager = LinearLayoutManager(this@MainActivity)
            }
            toolbar.aivProfile.setOnClickListener {
                preferences.setValue(Preferences.TOKEN, "")
                preferences.setValue(Preferences.NAME,"")
                preferences.setValue(Preferences.ID,"")
                startActivity(Intent(this@MainActivity, LoginActivity::class.java))
                finishAffinity()
            }
            toolbar.mtvToolbar.text = buildString {
                append("Hi, ")
                append(preferences.getValue(Preferences.NAME))
            }

            fabAdd.setOnClickListener {
                startActivity(Intent(this@MainActivity, AddStoryActivity::class.java))
            }

            fabMaps.setOnClickListener {
                startActivity(Intent(this@MainActivity, MapsActivity::class.java))
            }
        }

        mViewModel.getStory().observe(this) {
            storyAdapter.submitData(lifecycle, it)
        }
    }

    override fun onResume() {
        mViewModel.getStory().observe(this) {
            storyAdapter.submitData(lifecycle, it)
        }
        super.onResume()
    }
}