package com.learn.mydiary.ui.dialog

import android.os.Bundle
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.learn.mydiary.base.AppLoadingDialog
import com.learn.mydiary.databinding.DialogLoadingBinding

class AppDialog(fragmentManager: FragmentManager) : AppLoadingDialog<DialogLoadingBinding>(fragmentManager) {
    override fun onCreated(savedInstanceState: Bundle?) {
        isCancelable = false
    }

    override fun onViewBinding(viewGroup: ViewGroup?) = DialogLoadingBinding.inflate(layoutInflater, viewGroup, false)
}