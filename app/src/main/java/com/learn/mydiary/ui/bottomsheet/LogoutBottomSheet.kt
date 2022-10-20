package com.learn.mydiary.ui.bottomsheet

import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import com.learn.mydiary.base.BaseBottomSheet
import com.learn.mydiary.databinding.BottomsheetLogoutBinding
import com.learn.mydiary.ui.auth.login.LoginActivity
import com.learn.mydiary.util.preferences.Preferences
import javax.inject.Inject

class LogoutBottomSheet() : BaseBottomSheet<BottomsheetLogoutBinding>() {

    @Inject
    lateinit var preferences: Preferences

    override fun onViewBinding(viewGroup: ViewGroup?) =
        BottomsheetLogoutBinding.inflate(layoutInflater, viewGroup, false)

    override fun onCreated(savedInstanceState: Bundle?) {
        viewBinding.apply {
            mbLogout.setOnClickListener {
                preferences.setValue(Preferences.TOKEN, "")
                preferences.setValue(Preferences.NAME, "")
                preferences.setValue(Preferences.ID, "")
                startActivity(Intent(context, LoginActivity::class.java))
            }
            mbBack.setOnClickListener { dismiss() }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}