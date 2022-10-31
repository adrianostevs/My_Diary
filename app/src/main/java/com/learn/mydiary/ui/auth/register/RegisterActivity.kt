package com.learn.mydiary.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import com.learn.mydiary.base.BaseActivity
import com.learn.mydiary.data.remote.model.request.RegisterRequest
import com.learn.mydiary.databinding.ActivityRegisterBinding
import com.learn.mydiary.ui.dialog.AppDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class RegisterActivity : BaseActivity<ActivityRegisterBinding>() {

    private val mViewModel: RegisterViewModel by viewModels()

    private val mLoadingDialog by lazy { AppDialog(supportFragmentManager) }

    override fun onViewBinding() = ActivityRegisterBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {
        viewBinding.apply {
            if (etEmail.text != null || etName.text != null || etPassword.text != null) {
                mbRegister.isEnabled = true
                mbRegister.setOnClickListener {
                    mViewModel.register(
                        RegisterRequest(
                            name = etName.text.toString(),
                            email = etEmail.text.toString().trim(),
                            password = etPassword.text.toString().trim()
                        )
                    )
                }
            } else {
                mbRegister.isEnabled = false
            }

        }
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launchWhenCreated {
            mViewModel.registerEvent.collectLatest {
                when (it) {
                    is RegisterEvent.RegisterFailed -> {
                        Toast.makeText(this@RegisterActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is RegisterEvent.RegisterLoading -> {
                        if (it.isLoading) {
                            mLoadingDialog.show(this@RegisterActivity::class.java.simpleName)
                        } else {
                            mLoadingDialog.dismiss()
                        }
                    }
                    is RegisterEvent.RegisterSuccess -> {
                        Toast.makeText(this@RegisterActivity, it.register?.message, Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@RegisterActivity, RegisterSuccessActivity::class.java))
                        finishAffinity()
                    }
                }
            }
        }
    }
}