package com.testing.findyourbudy.activity

import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.TextUtils
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.testing.findyourbudy.R
import com.testing.findyourbudy.adapter.HobbiesAdapter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList

class ProfileActivity : AppCompatActivity(), View.OnClickListener {

    var userNameProfile:EditText? = null
    var userGenderProfile:EditText? = null
    var userCityProfile:EditText? = null
    var userContactProfile:EditText? = null
    var listView: RecyclerView? = null
    var okBtn:Button? = null
    var editBtn:Button? = null
    var hobbiesAdapter: HobbiesAdapter? = null
    var userImageProfile:ImageView? = null
    var cameraImage:ImageView? = null
    private var builder: AlertDialog.Builder? = null
    private var progressBar: ProgressBar? = null
    private var imageByteArray: ByteArray? = null
    private var imageByteArrayBase_64_string: String? = null
    private var imageCap: String? = null
    private var hobyList:ArrayList<String>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        initView()
        initInstance()
        setListenres()
        setAdapter()
    }

    private fun initView() {
        userNameProfile = findViewById(R.id.profileUserName)
        userGenderProfile = findViewById(R.id.userGender)
        userCityProfile = findViewById(R.id.userCity)
        userContactProfile = findViewById(R.id.profileContactNo)
        listView = findViewById(R.id.listViewProfile)
        okBtn = findViewById(R.id.okButtonProfile)
        editBtn = findViewById(R.id.editButtonProfile)
        userImageProfile = findViewById(R.id.profileImage)
        cameraImage = findViewById(R.id.cameraImageProfile)
        progressBar = findViewById(R.id.progBar)
    }

    private fun initInstance() {
        builder = AlertDialog.Builder(this@ProfileActivity)
        hobyList = ArrayList()

    }

    private fun setListenres() {
      okBtn!!.setOnClickListener(this)
      editBtn!!.setOnClickListener(this)
      userImageProfile!!.setOnClickListener(this)
      cameraImage!!.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when(v!!.id){

            R.id.okButtonProfile -> {
                val intent = Intent(this@ProfileActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.editButtonProfile -> {
                updateDataTextEditable()

            }

            R.id.profileImage -> {
                if (editBtn!!.getText() == "UPDATE") {
                } else {
                    selectImage()
                }

            }

            R.id.cameraImageProfile -> {

                if (editBtn!!.getText() == "UPDATE") {
                } else {
                    selectImage()
                }
            }

        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val intent = Intent(this@ProfileActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
        return true
    }

    // edit text editable function
    private fun updateDataTextEditable() {

        if (editBtn!!.text.equals("UPDATE")){
            userNameProfile!!.setEnabled(false)
            userGenderProfile!!.setEnabled(false)
            userCityProfile!!.setEnabled(false)
            userContactProfile!!.setEnabled(false)
            userImageProfile!!.setClickable(false)
            cameraImage!!.setVisibility(View.GONE)
            editBtn!!.setText("EDIT")

        }else {
            userNameProfile!!.setEnabled(true)
            userGenderProfile!!.setEnabled(true)
            userCityProfile!!.setEnabled(true)
            userContactProfile!!.setEnabled(true)
            userImageProfile!!.setClickable(true)
            cameraImage!!.setVisibility(View.VISIBLE)
            editBtn!!.setText("UPDATE")
        }
    }

    private fun selectImage() {
        val options = arrayOf<CharSequence>("Take Photo", "Choose from Gallery", "Cancel")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose your profile picture")
        builder.setItems(options) { dialog, item ->
            if (options[item] == "Take Photo") {
                val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(takePicture, 0)
            } else if (options[item] == "Choose from Gallery") {
                val pickPhoto =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
                startActivityForResult(pickPhoto, 1)
            } else if (options[item] == "Cancel") {
                dialog.dismiss()
            }
        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != RESULT_CANCELED) {
            when (requestCode) {
                0 -> if (resultCode == RESULT_OK && data != null) {
                    val selectedImage = Objects.requireNonNull(data.extras)!!["data"] as Bitmap?
                    val uriString = data.extras!!["data"].toString()
                    assert(selectedImage != null)
                    imageByteArray = getBytesFromBitmap(selectedImage!!)
                    imageByteArrayBase_64_string =
                        Base64.encodeToString(imageByteArray, Base64.DEFAULT)
                    imageCap = imageByteArrayBase_64_string!!.replace("\t", "").replace("\n", "")
                        .replace("\\", "")

                    //  String pathName = saveToInternalStorage(selectedImage);
                    val myFile = File(uriString)
                    //  String pathName = myFile.getAbsolutePath();
                    // //System.out.println(">>>>>> main activity on actibity capture image path" + pathName);
                    //  SessionManager.getInstance(ProfileOrganizationActivity.this).saveImage(pathName);
                    userImageProfile!!.setImageBitmap(selectedImage)
                }
                1 -> if (resultCode == RESULT_OK && data != null) {
                    val selectedImage = data.data
                    //
                    try {
                        val bitmaps =
                            MediaStore.Images.Media.getBitmap(this.contentResolver, selectedImage)
                        val byteArray: ByteArray = getBytesFromBitmap(bitmaps)
                        val encodeded = Base64.encodeToString(byteArray, Base64.DEFAULT)
                        //                            String imageCap = imageByteArrayBase_64_stringExistanceUser.replace("\t", "").replace("\n", "").replace("\\", "");


                        //    String pathName = saveToInternalStorage(bitmaps);

                        // Uri uri = data.getData();
                        val uriString = selectedImage.toString()
                        val myFile = File(uriString)

                        // SessionManager.getInstance(ProfileOrganizationActivity.this).saveImage(pathName);
                        userImageProfile!!.setImageBitmap(bitmaps)
                        // //System.out.println(">>>>>> main activity on actibity gallery image path" + pathName);

//                            break;
                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }

            }
        }
    }

    private fun getBytesFromBitmap(bitmap: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
        return outputStream.toByteArray()
    }

    private fun isValid(): Boolean {
        return if (TextUtils.isEmpty(userNameProfile!!.text)) {
            userNameProfile!!.error = "Enter User Name"
            userNameProfile!!.isFocusable = true
            false

        }else if (TextUtils.isEmpty(userGenderProfile!!.text)) {
            userGenderProfile!!.error = "Enter Gender"
            userGenderProfile!!.isFocusable = true
            false

        } else if (TextUtils.isEmpty(userCityProfile!!.text)) {
            userCityProfile!!.error = "Enter Password"
            userCityProfile!!.isFocusable = true
            false

        }else if (TextUtils.isEmpty(userContactProfile!!.text)) {
            userContactProfile!!.error = "Enter Mobile No"
            userContactProfile!!.isFocusable = true
            false

        } else if (userContactProfile!!.text.length != 10) {
            userContactProfile!!.error = "Enter 10 digit mobile no"
            userContactProfile!!.isFocusable = true
            false

        } else {
            true
        }
    }

    private fun setAdapter(){

      hobbiesAdapter = HobbiesAdapter(this@ProfileActivity, hobyList!!)
      listView!!.layoutManager = LinearLayoutManager(this@ProfileActivity)
      listView!!.adapter = hobbiesAdapter

   }

}