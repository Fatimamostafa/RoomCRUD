package com.fatimamostafa.roomcrud.ui.update

import android.Manifest
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.util.Base64
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.Observer
import com.fatimamostafa.roomcrud.R
import com.fatimamostafa.roomcrud.base.BaseActivity
import com.fatimamostafa.roomcrud.database.EmployeeModel
import com.fatimamostafa.roomcrud.di.ViewModelInjectionField
import com.fatimamostafa.roomcrud.di.qualifiers.ViewModelInjection
import com.fatimamostafa.roomcrud.utils.CircleTransformation
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import com.squareup.picasso.Picasso
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_update_employee.*
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.inject.Inject

class UpdateEmployeeActivity : BaseActivity() {
    companion object {
        @JvmStatic
        var employee: EmployeeModel? = null
    }

    @Inject
    @ViewModelInjection
    lateinit var viewModel: ViewModelInjectionField<UpdateEmployeeVM>

    override fun layoutRes() = R.layout.activity_update_employee

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
        btn_submit.setOnClickListener {
            if (et_first_name.text.isNullOrBlank()
                || et_last_name.text.isNullOrBlank()
                || et_age.text.isNullOrBlank()
            ) {
                Toast.makeText(
                    this@UpdateEmployeeActivity,
                    "Please fill all the above fields",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            if (viewModel.get().imagePath.value == "") {
                Toast.makeText(
                    this@UpdateEmployeeActivity,
                    "Please upload image",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }


            if (employee == null)
                viewModel.get().addEmployee(
                    et_first_name.text.toString(),
                    et_last_name.text.toString(),
                    et_age.text.toString()
                )
            else
                viewModel.get().updateEmployee(
                    employee?.id!!,
                    et_first_name.text.toString(),
                    et_last_name.text.toString(),
                    et_age.text.toString()
                )
        }
        btn_upload.setOnClickListener {
            Dexter.withActivity(this)
                .withPermission(Manifest.permission.CAMERA)
                .withListener(object : PermissionListener {
                    override fun onPermissionGranted(response: PermissionGrantedResponse?) {
                        CropImage.activity()
                            .setGuidelines(CropImageView.Guidelines.ON)
                            .start(this@UpdateEmployeeActivity)
                    }

                    override fun onPermissionRationaleShouldBeShown(
                        permission: PermissionRequest?,
                        token: PermissionToken?
                    ) {

                    }

                    override fun onPermissionDenied(response: PermissionDeniedResponse?) {

                    }

                }

                )
                .check()
        }

        rg_gender.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_male) {
                viewModel.get().gender.postValue("Male")
            } else {
                viewModel.get().gender.postValue("Female")
            }
        }


        init()
    }

    private fun init() {

        viewModel.get().gender.postValue("Male")
        viewModel.get().imagePath.postValue("")
        viewModel.get().updated.observe(this, Observer {
            it.let { added ->
                if (added)
                    onBackPressed()
            }
        })
        toolbar.title = getString(R.string.add_employee)
        btn_submit.text = getString(R.string.add_employee)
        employee?.let {
            et_first_name.setText(it.firstName)
            et_last_name.setText(it.lastName)
            et_age.setText(it.age.toString())
            viewModel.get().imagePath.postValue(it.imageUrl)
            if (it.imageUrl.contains("https://")) {
                Picasso.get()
                    .load(it.imageUrl)
                    .error(R.drawable.error_list_image)
                    .placeholder(R.drawable.default_list_image)
                    .transform(CircleTransformation())
                    .into(iv_employee)

            } else {
                val imageBytes = Base64.decode(it.imageUrl, Base64.DEFAULT)
                val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
                iv_employee.setImageBitmap(decodedImage)
            }


            viewModel.get().gender.postValue(it.gender)
            when (it.gender) {
                "Male" -> {
                    rb_male.isChecked = true
                }
                else -> {
                    rb_female.isChecked = true
                }
            }
            btn_submit.text = getString(R.string.update_employee)

            toolbar.title = getString(R.string.update_employee)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, imageReturnedIntent: Intent?) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent)
        when (requestCode) {
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(imageReturnedIntent)
                if (resultCode == RESULT_OK) {
                    try {
                        val resultUri = result.uri

                        var stringImage = encode(resultUri)
                        decode(stringImage)


                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {

                }
            }
        }

    }

    private fun encode(imageUri: Uri): String {
        val input = contentResolver.openInputStream(imageUri)
        val image = BitmapFactory.decodeStream(input, null, null)
        //encode image to base64 string
        val baos = ByteArrayOutputStream()
        image?.compress(Bitmap.CompressFormat.JPEG, 70, baos)
        var imageBytes = baos.toByteArray()
        val imageString = Base64.encodeToString(imageBytes, Base64.DEFAULT)
        return imageString
    }

    private fun decode(imageString: String) {
        //decode base64 string to image
        Log.d("Image size", imageString.length.toString())
        val imageBytes = Base64.decode(imageString, Base64.DEFAULT)
        val decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        iv_employee.setImageBitmap(decodedImage)
        viewModel.get().imagePath.postValue(imageString)
    }


}