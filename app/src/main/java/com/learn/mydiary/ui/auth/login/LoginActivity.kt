package com.learn.mydiary.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.learn.mydiary.R
import com.learn.mydiary.base.BaseActivity
import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.remote.model.response.ResultResponse
import com.learn.mydiary.databinding.ActivityLoginBinding
import com.learn.mydiary.ui.auth.register.RegisterActivity
import com.learn.mydiary.ui.auth.register.RegisterSuccessActivity
import com.learn.mydiary.ui.dialog.AppDialog
import com.learn.mydiary.ui.main.MainActivity
import com.learn.mydiary.util.preferences.Preferences
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    private val mViewModel: LoginViewModel by viewModels()

    private val mLoadingDialog by lazy { AppDialog(supportFragmentManager) }

    @Inject
    lateinit var preferences: Preferences

    override fun onViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {
        val token = preferences.getValue(Preferences.TOKEN)
//        if (token!!.isNotEmpty()){
//            startActivity(Intent())
//            finish()
//        }

        viewBinding.apply {
            lavLogin.setAnimation(R.raw.login)

            mtvRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }

            if (etEmail.text == null || etPassword.text == null) {
                mbLogin.isEnabled = false
            } else {
                mbLogin.isEnabled = true
                mbLogin.setOnClickListener {
                    mViewModel.login(
                        LoginRequest(
                            email = etEmail.text.toString().trim(),
                            password = etPassword.text.toString().trim()
                        )
                    )
                }
            }


        }

        observeData()
    }

    private fun observeData() {
        lifecycleScope.launchWhenCreated {
            mViewModel.loginEvent.collectLatest {
                when (it) {
                    is LoginEvent.LoginLoading -> {
                        if (it.isLoading) {
                            mLoadingDialog.show(this@LoginActivity::class.java.simpleName)
                        } else {
                            mLoadingDialog.dismiss()
                        }
                    }
                    is LoginEvent.LoginFailure -> {
                        Toast.makeText(this@LoginActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is LoginEvent.LoginSuccess -> {
                        Toast.makeText(this@LoginActivity, it.login?.message, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                    }
                }
            }
        }
    }


}