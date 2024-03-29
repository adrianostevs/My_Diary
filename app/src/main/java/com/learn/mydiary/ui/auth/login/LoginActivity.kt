package com.learn.mydiary.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import com.learn.mydiary.R
import com.learn.mydiary.base.BaseActivity
import com.learn.mydiary.data.remote.model.request.LoginRequest
import com.learn.mydiary.data.remote.model.response.ResultResponse
import com.learn.mydiary.databinding.ActivityLoginBinding
import com.learn.mydiary.ui.auth.register.RegisterActivity
import com.learn.mydiary.ui.dialog.AppDialog
import com.learn.mydiary.ui.main.MainActivity
import com.learn.mydiary.util.preferences.Preferences
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {

    @Inject
    lateinit var preferences: Preferences

    private val mViewModel: LoginViewModel by viewModels()

    private val mLoadingDialog by lazy { AppDialog(supportFragmentManager) }

    override fun onViewBinding() = ActivityLoginBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {

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
                    ).observe(this@LoginActivity){
                        when (it) {
                            is ResultResponse.Loading -> {
                                if (it.isLoading) {
                                    mLoadingDialog.show(this@LoginActivity::class.java.simpleName)
                                } else {
                                    mLoadingDialog.dismiss()
                                }
                            }
                            is ResultResponse.Failure -> {
                                Toast.makeText(this@LoginActivity, it.errorMessage, Toast.LENGTH_SHORT).show()
                            }
                            is ResultResponse.Success -> {
                                preferences.setValue(Preferences.TOKEN, it.data.loginResult?.token.toString())
                                preferences.setValue(Preferences.NAME, it.data.loginResult?.name.toString())
                                Toast.makeText(this@LoginActivity, it.data.message, Toast.LENGTH_SHORT).show()
                                startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                                finishAffinity()
                            }
                        }
                    }
                }
            }


        }

    }

}