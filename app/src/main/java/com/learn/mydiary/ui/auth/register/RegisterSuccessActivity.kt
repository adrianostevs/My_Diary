package com.learn.mydiary.ui.auth.register

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.learn.mydiary.R
import com.learn.mydiary.base.BaseActivity
import com.learn.mydiary.databinding.ActivityRegisterSuccessBinding
import com.learn.mydiary.ui.auth.login.LoginActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterSuccessActivity : BaseActivity<ActivityRegisterSuccessBinding>() {
    override fun onViewBinding() = ActivityRegisterSuccessBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {

        viewBinding.apply {
            mbLogin.setOnClickListener {
                startActivity(Intent(this@RegisterSuccessActivity, LoginActivity::class.java))
            }

            lavRegister.setAnimation(R.raw.register)
        }
        playAnimation()
    }

    private fun playAnimation() {
        val title = ObjectAnimator.ofFloat(viewBinding.mtvTitle, View.ALPHA, 1f).setDuration(2000)
        val subtitle = ObjectAnimator.ofFloat(viewBinding.mtvSubtitle, View.ALPHA, 1f).setDuration(2000)

        AnimatorSet().apply {
            playSequentially(title, subtitle)
            start()
        }
    }

    override fun onBackPressed() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}