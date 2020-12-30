package com.sivajonah.todo.userinfo

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import coil.load
import com.sivajonah.todo.BuildConfig
import com.sivajonah.todo.R
import com.sivajonah.todo.model.UserInfo
import com.sivajonah.todo.viewmodel.UserInfoViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class UserInfoActivity : AppCompatActivity() {

    private val viewModel: UserInfoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_info)

        val takePictureButton = this?.findViewById<Button>(R.id.take_picture_button)
        val pickPictureButton = this?.findViewById<Button>(R.id.upload_image_button)
        val imageView = this?.findViewById<ImageView>(R.id.image_view)
        val updateButton = this?.findViewById<Button>(R.id.update_button)
        val editName = this?.findViewById<TextView>(R.id.editName)
        val editFirstName = this?.findViewById<TextView>(R.id.editFirstName)
        val editMail = this?.findViewById<TextView>(R.id.editMail)

        takePictureButton?.setOnClickListener{
            askCameraPermissionAndOpenCamera()
        }

        pickPictureButton?.setOnClickListener {
            pickImage()
        }

        updateButton?.setOnClickListener {
            val newUserInfo = UserInfo(
                firstName = editFirstName.text.toString(),
                lastName = editName.text.toString(),
                email = editMail.text.toString(),
                avatar = viewModel.userInfo.value!!.avatar)
            viewModel.update(newUserInfo)
        }

        viewModel.userInfo.observe(this, Observer { userInfo ->
            // on check s'il y a besoin de reload chaque partie

            if(editName.text != userInfo.lastName) {
                editName.text = userInfo.lastName
            }

            if(editFirstName.text != userInfo.firstName) {
                editFirstName.text = userInfo.firstName
            }

            if(editMail.text != userInfo.email) {
                editMail.text = userInfo.email
            }

            if(imageView.tag != userInfo.avatar) {
                imageView.load(userInfo.avatar)
                imageView.tag = userInfo.avatar
            }
        })

        lifecycleScope.launch {
            viewModel.getInfo()
        }
    }

    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) openCamera()
            else showExplanationDialog()
        }

    private fun requestCameraPermission() =
        requestPermissionLauncher.launch(Manifest.permission.CAMERA)

    private fun askCameraPermissionAndOpenCamera() {
        when {
            ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED -> openCamera()
            shouldShowRequestPermissionRationale(Manifest.permission.CAMERA) -> showExplanationDialog()
            else -> requestCameraPermission()
        }
    }

    private fun showExplanationDialog() {
        AlertDialog.Builder(this).apply {
            setMessage("On a besoin de la caméra sivouplé ! 🥺")
            setPositiveButton("Bon, ok") { _, _ ->
                requestCameraPermission()
            }
            setCancelable(true)
            show()
        }
    }

    // create a temp file and get a uri for it
    private val photoUri by lazy {
        FileProvider.getUriForFile(
            this,
            BuildConfig.APPLICATION_ID +".fileprovider",
            File.createTempFile("avatar", ".jpeg", externalCacheDir)
        )
    }

    // register
    private val takePicture = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) handleImage(photoUri)
        else Toast.makeText(this, "Erreur ! 😢", Toast.LENGTH_LONG).show()
    }

    // use
    private fun openCamera() = takePicture.launch(photoUri)

    // convert
    private fun convert(uri: Uri) =
        MultipartBody.Part.createFormData(
            name = "avatar",
            filename = "temp.jpeg",
            body = contentResolver.openInputStream(uri)!!.readBytes().toRequestBody()
        )

    private fun handleImage(uri: Uri) {
        lifecycleScope.launch{
            viewModel.updateAvatar(convert(uri))
        }
    }

    // register
    private val pickInGallery =
        registerForActivityResult(ActivityResultContracts.GetContent()) {
            handleImage(it)
        }

    // use
    private fun pickImage() = pickInGallery.launch("image/*")
}