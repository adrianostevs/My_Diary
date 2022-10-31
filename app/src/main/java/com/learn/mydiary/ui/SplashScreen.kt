package com.learn.mydiary.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.learn.mydiary.R
import com.learn.mydiary.base.BaseActivity
import com.learn.mydiary.databinding.SplashScreenBinding
import com.learn.mydiary.ui.auth.login.LoginActivity
import com.learn.mydiary.ui.main.MainActivity
import com.learn.mydiary.util.preferences.Preferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashScreen: BaseActivity<SplashScreenBinding>() {

    @Inject
    lateinit var preferences: Preferences

    override fun onViewBinding()= SplashScreenBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {
        viewBinding.lavSplash.setAnimation(R.raw.book)
        Handler(mainLooper).postDelayed({
            if(preferences.getValue(Preferences.TOKEN).isNullOrEmpty()){
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            }
        }, 3000L)
    }
}