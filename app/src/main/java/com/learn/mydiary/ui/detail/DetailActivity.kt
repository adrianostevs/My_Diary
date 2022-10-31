package com.learn.mydiary.ui.detail

import android.os.Bundle
import coil.load
import com.learn.mydiary.base.BaseActivity
import com.learn.mydiary.databinding.ActivityDetailBinding
import com.learn.mydiary.domain.model.Story
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailActivity: BaseActivity<ActivityDetailBinding>() {

    override fun onViewBinding()= ActivityDetailBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {

        val data = intent.getParcelableExtra<Story>(INTENT_DATA)
        if (data != null){
            viewBinding.apply {
                aivPicture.load(data.photoUrl)
                mtvTitle.text = data.name
                mtvDescription.text = data.description
            }
        }
    }

    companion object {
        const val INTENT_DATA = "DATA"
    }
}