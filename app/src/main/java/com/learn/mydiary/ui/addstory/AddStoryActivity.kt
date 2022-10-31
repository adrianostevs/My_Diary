package com.learn.mydiary.ui.addstory

import android.Manifest
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.learn.mydiary.base.BaseActivity
import com.learn.mydiary.data.remote.model.request.AddStoryRequest
import com.learn.mydiary.databinding.ActivityAddStoryBinding
import com.learn.mydiary.ui.addstory.camera.CameraActivity
import com.learn.mydiary.ui.dialog.AppDialog
import com.learn.mydiary.ui.main.MainActivity
import com.learn.mydiary.util.camera.reduceFileImage
import com.learn.mydiary.util.camera.rotateBitmap
import com.learn.mydiary.util.camera.uriToFile
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import java.io.File

@AndroidEntryPoint
class AddStoryActivity : BaseActivity<ActivityAddStoryBinding>() {

    private val mViewModel: AddStoryViewModel by viewModels()

    private var getFile: File? = null

    private val mLoadingDialog by lazy { AppDialog(supportFragmentManager) }

    override fun onViewBinding() = ActivityAddStoryBinding.inflate(layoutInflater)

    override fun onCreated(savedInstanceState: Bundle?) {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                10
            )
        }

        viewBinding.apply {
            mbCamera.setOnClickListener {
                val intent = Intent(this@AddStoryActivity, CameraActivity::class.java)
                launchIntentCamera.launch(intent)
            }
            mbGallery.setOnClickListener {
                val intent = Intent()
                intent.action = ACTION_GET_CONTENT
                intent.type = "image/*"
                val chooser = Intent.createChooser(intent, "Choose a Picture")
                launcherIntentGallery.launch(chooser)
            }

        }

        observeData()
    }

    override fun onResume() {
        viewBinding.apply {
            if (getFile == null || etDescription.text == null) {
                mbAddStory.isEnabled = false
            } else {
                mbAddStory.isEnabled = true
                mbAddStory.setOnClickListener {
                    mViewModel.setStory(
                        AddStoryRequest(
                            photo = getFile,
                            description = etDescription.text.toString().trim()
                        )
                    )
                }
            }
        }
        super.onResume()
    }

    private fun observeData() {
        lifecycleScope.launchWhenCreated {
            mViewModel.storyEvent.collectLatest {
                when (it) {
                    is AddStoryEvent.AddStorySuccess -> {
                        Toast.makeText(
                            this@AddStoryActivity,
                            it.response?.message,
                            Toast.LENGTH_SHORT
                        ).show()
                        startActivity(Intent(this@AddStoryActivity, MainActivity::class.java))
                        finishAffinity()
                    }
                    is AddStoryEvent.AddStoryFailed -> {
                        Toast.makeText(this@AddStoryActivity, it.message, Toast.LENGTH_SHORT).show()
                    }
                    is AddStoryEvent.AddStoryLoading -> {
                        if (it.isLoading) {
                            mLoadingDialog.show(this@AddStoryActivity::class.java.simpleName)
                        } else {
                            mLoadingDialog.dismiss()
                        }
                    }
                }
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 10) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private val launchIntentCamera =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == CAMERA_X_RESULT) {
                val myFile = it.data?.getSerializableExtra("picture") as File
                val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean
                val file = reduceFileImage(myFile)

                getFile = file

                val result = rotateBitmap(
                    BitmapFactory.decodeFile(myFile.path),
                    isBackCamera
                )

                viewBinding.aivPicture.setImageBitmap(result)
            }
        }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri
            val myFile = uriToFile(selectedImg, this@AddStoryActivity)
            val file = reduceFileImage(myFile)

            getFile = file
            viewBinding.aivPicture.setImageURI(selectedImg)
        }
    }

    companion object {
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        const val CAMERA_X_RESULT = 200
    }
}