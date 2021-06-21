package com.testing.findyourbudy.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.testing.findyourbudy.Manager.SessionManager
import com.testing.findyourbudy.R
import java.util.Calendar.getInstance

class OtpActivity : AppCompatActivity(), View.OnClickListener {

    private var inputCode1: EditText? = null
    private  var inputCode2:EditText? = null
    private  var inputCode3:EditText? = null
    private  var inputCode4:EditText? = null
    private  var inputCode5:EditText? = null
    private  var inputCode6:EditText? = null
    private var verifyButton: Button? = null
    private var resendOtpButton: TextView? = null
    private var progressBar: ProgressBar? = null
    private var builder: AlertDialog.Builder? = null
    var forwhichactivity: String? = null
    var jsondata: String? = null
    var backLayout: LinearLayout? = null
    private val otpbackBtn: TextView? = null
    private var verificationId: String? = null
    private var phoneNoIntent: String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_otp)

        initView()
        initInstance()
        setListeners()
        setupOTPInputs()
    }

    private fun initView() {
        verifyButton = findViewById(R.id.buttonVerify)
        inputCode1 = findViewById(R.id.inputCode1)
        inputCode2 = findViewById(R.id.inputCode2)
        inputCode3 = findViewById(R.id.inputCode3)
        inputCode4 = findViewById(R.id.inputCode4)
        inputCode5 = findViewById(R.id.inputCode5)
        inputCode6 = findViewById(R.id.inputCode6)
        resendOtpButton = findViewById(R.id.textResentOTP)
        progressBar = findViewById(R.id.progreeBar)

        verificationId = intent.getStringExtra("verificationId")
        phoneNoIntent = intent.getStringExtra("phoneNo")


        //for system navigation bar color #E58221
        window.navigationBarColor = resources.getColor(R.color.colorPrimaryDark) //ae153b
    }

    private fun initInstance() {

        builder = AlertDialog.Builder(this@OtpActivity)

    }


    private fun setListeners() {
        verifyButton!!.setOnClickListener(this)
        resendOtpButton!!.setOnClickListener(this)
    }

    /*

     * Description   : function for otp validation  .
     * Updated Name  :
     * Updated Date  :
     * Description
     */
    private fun isValid(): Boolean {
        return if (inputCode1!!.text.toString().trim { it <= ' ' }
                .isEmpty() || inputCode2!!.text.toString().trim { it <= ' ' }
                .isEmpty() || inputCode3!!.text.toString().trim { it <= ' ' }.isEmpty() ||
            inputCode4!!.text.toString().trim { it <= ' ' }
                .isEmpty() || inputCode5!!.text.toString().trim { it <= ' ' }
                .isEmpty() || inputCode6!!.text.toString().trim { it <= ' ' }.isEmpty()
        ) {
            Toast.makeText(this@OtpActivity, "Please enter valid code", Toast.LENGTH_SHORT)
                .show()
            false
        } else {
            true
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.buttonVerify -> {
                if (isValid()) {
                    val otpNumber = inputCode1!!.text.toString() +
                            inputCode2!!.text.toString() +
                            inputCode3!!.text.toString() +
                            inputCode4!!.text.toString() +
                            inputCode5!!.text.toString() +
                            inputCode6!!.text.toString()

                    if (verificationId != null) {
                        progressBar!!.visibility = View.VISIBLE
                        verifyButton!!.visibility = View.INVISIBLE

                        val credential = PhoneAuthProvider.getCredential(
                            verificationId!!,
                            otpNumber
                        )
                        FirebaseAuth.getInstance().signInWithCredential(credential)
                            .addOnCompleteListener(this) { task ->
                                if (task.isSuccessful) {
                                    SessionManager(this@OtpActivity)
                                        .setMobileNo(phoneNoIntent)
                                    val intent = Intent(
                                        this@OtpActivity,
                                        MainActivity::class.java
                                    )
                                    intent.putExtra("phoneNo", phoneNoIntent)
                                    intent.flags =
                                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                    startActivity(intent)

                                } else {
                                    progressBar!!.visibility = View.GONE
                                    verifyButton!!.visibility = View.VISIBLE
                                    Toast.makeText(
                                        this@OtpActivity,
                                        "The Verification code entered was invalid",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                }
                            }

                    }

                }

            }
            R.id.textResentOTP -> {
                val intent = Intent(this@OtpActivity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }
    }

    private fun setupOTPInputs() {
        inputCode1!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.toString().trim { it <= ' ' }.isEmpty()) {
                    inputCode2!!.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        inputCode2!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.toString().trim { it <= ' ' }.isEmpty()) {
                    inputCode3!!.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        inputCode3!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.toString().trim { it <= ' ' }.isEmpty()) {
                    inputCode4!!.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        inputCode4!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.toString().trim { it <= ' ' }.isEmpty()) {
                    inputCode5!!.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
        inputCode5!!.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!s.toString().trim { it <= ' ' }.isEmpty()) {
                    inputCode6!!.requestFocus()
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }

}