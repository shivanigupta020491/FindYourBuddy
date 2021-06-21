package com.testing.findyourbudy.activity

import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender.SendIntentException
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import com.testing.findyourbudy.R

class LoginActivity : AppCompatActivity(), View.OnClickListener {

    private var userNameEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var loginButton: Button? = null
    private var signUpBtn: TextView? = null
    private var forgotPasswordButton: TextView? = null
    private var facebookIcon: ImageView? = null
    private var googleIcon: ImageView? = null
    private var builder: AlertDialog.Builder? = null
    private var progressBar: ProgressBar? = null
    private val CREDENTIAL_PICKER_REQUEST = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        initView()
        initInstances()
        setListeners()
        setFcmTokenFromServer()
    }

    private fun initView() {

        loginButton = findViewById(R.id.loginButton)
        userNameEditText = findViewById(R.id.userNameEditText)
        passwordEditText = findViewById(R.id.passwordEditText)
        forgotPasswordButton = findViewById(R.id.forgotPasswordText)
        signUpBtn = findViewById(R.id.signUp)
        facebookIcon = findViewById(R.id.facebookImage)
        googleIcon = findViewById(R.id.googleImage)
        progressBar = findViewById(R.id.progBar)
    }

    private fun initInstances() {
        builder = AlertDialog.Builder(this@LoginActivity)
    }

    private fun setListeners() {
        loginButton!!.setOnClickListener(this)
        signUpBtn!!.setOnClickListener(this)
        forgotPasswordButton!!.setOnClickListener(this)
        facebookIcon!!.setOnClickListener(this)
        googleIcon!!.setOnClickListener(this)

    }

    private fun setFcmTokenFromServer() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.loginButton -> {

                //if (isValid()) {
                    val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                    startActivity(intent)
                    finish()
               // }
            }

            R.id.signUp -> {
                val intent = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.forgotPasswordText -> {
                val intent = Intent(this@LoginActivity, ChatboxActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun isValid(): Boolean {
        return if (TextUtils.isEmpty(userNameEditText!!.text)) {
            userNameEditText!!.error = "Enter Mobile No"
            false
        } else if (userNameEditText!!.text.length != 10) {
            userNameEditText!!.error = "Enter 10 digit mobile no"
            false
        } else if (TextUtils.isEmpty(passwordEditText!!.text)) {
            passwordEditText!!.error = "Enter Password"
            false
        } else if (passwordEditText!!.text.length < 8) {
            passwordEditText!!.error = "Password is too short"
            false
        } else {
            true
        }
    }

    private fun showAlertDialog(title: String, message: String) {
        builder!!.setTitle(title)
        builder!!.setMessage(message)
        builder!!.setPositiveButton(
            "OK",
            DialogInterface.OnClickListener { dialog, which -> // Do nothing but close the dialog
                dialog.dismiss()
            } //builder.create();
        )

//       alertDialog = builder.create();
//       alertDialog.show();
////
        builder!!.show()
    }

//    fun takeAutomaticMobileNo() {
//
//        val hintRequest = HintRequest.Builder()
//            .setPhoneNumberIdentifierSupported(true)
//            .build()
//         val intent = Credentials.getClient(this@LoginActivity).getHintPickerIntent(hintRequest)
//        try {
//            startIntentSenderForResult(
//                intent.intentSender,
//                CREDENTIAL_PICKER_REQUEST,
//                null,
//                0,
//                0,
//                0,
//                Bundle()
//            )
//        } catch (e: SendIntentException) {
//            e.printStackTrace()
//        }
//    }
}