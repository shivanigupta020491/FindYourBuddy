package com.testing.findyourbudy.activity

import android.content.DialogInterface
import android.content.Intent
import android.content.IntentSender
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

import com.testing.findyourbudy.R

class SignupActivity : AppCompatActivity(), View.OnClickListener {

    private var userNameEditText: EditText? = null
    private var contactEditText: EditText? = null
    private var emailEditText: EditText? = null
    private var passwordEditText: EditText? = null
    private var confirmPasswordEditText: EditText? = null
    private var loginButton: TextView? = null
    private var signUpBtn: Button? = null
    private var builder: AlertDialog.Builder? = null
    private var progressBar: ProgressBar? = null
    private val CREDENTIAL_PICKER_REQUEST = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        initView()
        initInstances()
        setListeners()
    }

    private fun initView() {

        loginButton = findViewById(R.id.login)
        userNameEditText = findViewById(R.id.userNameSignup)
        contactEditText = findViewById(R.id.contactNoSignup)
        emailEditText = findViewById(R.id.emailSignup)
        passwordEditText = findViewById(R.id.passwordSignup)
        confirmPasswordEditText = findViewById(R.id.confirmPasswordSignup)
        signUpBtn = findViewById(R.id.signupButton)
        progressBar = findViewById(R.id.progBar)
    }

    private fun initInstances() {
        builder = AlertDialog.Builder(this@SignupActivity)
    }

    private fun setListeners() {
        loginButton!!.setOnClickListener(this)
        signUpBtn!!.setOnClickListener(this)


    }

    private fun setFcmTokenFromServer() {

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.signupButton -> {

                if (isValid()) {
                    val intent = Intent(this@SignupActivity, OtpActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            }

            R.id.login -> {
                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }

            R.id.forgotPasswordText -> {

            }
        }
    }

    private fun isValid(): Boolean {
        return if (TextUtils.isEmpty(userNameEditText!!.text)) {
            userNameEditText!!.error = "Enter User Name"
            userNameEditText!!.isFocusable = true
            false

        }else if (TextUtils.isEmpty(contactEditText!!.text)) {
            contactEditText!!.error = "Enter Mobile No"
            contactEditText!!.isFocusable = true
            false

        } else if (contactEditText!!.text.length != 10) {
            contactEditText!!.error = "Enter 10 digit mobile no"
            contactEditText!!.isFocusable = true
            false

        }else if (TextUtils.isEmpty(emailEditText!!.text)) {
            emailEditText!!.error = "Enter Email"
            emailEditText!!.isFocusable = true
            false

        } else if (TextUtils.isEmpty(passwordEditText!!.text)) {
            passwordEditText!!.error = "Enter Password"
            passwordEditText!!.isFocusable = true
            false

        } else if (passwordEditText!!.text.length < 8) {
            passwordEditText!!.error = "Password is too short"
            passwordEditText!!.isFocusable = true
            false

        }  else if (TextUtils.isEmpty(confirmPasswordEditText!!.text)) {
            confirmPasswordEditText!!.error = "Enter Password"
            confirmPasswordEditText!!.isFocusable = true
             false

         }else if (!((confirmPasswordEditText!!.getText().toString()) == passwordEditText!!.getText().toString())) {
        confirmPasswordEditText!!.error = "Password does not match"
        confirmPasswordEditText!!.isFocusable = true
        return false
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
//        val intent = Credentials.getClient(this@SignupActivity).getHintPickerIntent(hintRequest)
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
//        } catch (e: IntentSender.SendIntentException) {
//            e.printStackTrace()
//        }
//    }
}