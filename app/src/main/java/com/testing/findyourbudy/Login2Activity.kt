package com.testing.findyourbudy

import android.content.Intent
import android.graphics.Color
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.tasks.OnSuccessListener
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthProvider.ForceResendingToken
import com.google.firebase.auth.PhoneAuthProvider.OnVerificationStateChangedCallbacks
import com.google.firebase.database.*
import com.google.firebase.functions.FirebaseFunctions
import com.google.firebase.functions.HttpsCallableResult
import com.testing.findyourbudy.activity.MainActivity
import com.testing.findyourbudy.activity.OtpActivity
import java.util.*
import java.util.concurrent.TimeUnit

class Login2Activity : AppCompatActivity(), View.OnClickListener {

    private var loginButton: Button? = null
    private var processText: TextView? = null
    private var userNameEditText: TextInputEditText? = null
    private var builder: AlertDialog.Builder? = null
    private val loginId: String? = null
    private var progressBar: ProgressBar? = null
    private val alertDialog: AlertDialog? = null
    private var auth: FirebaseAuth? = null
    private var mdatabase: FirebaseDatabase? = null
    private var mDatabaseReference: DatabaseReference? = null
    private var mCallBacks: OnVerificationStateChangedCallbacks? = null
    private val CREDENTIAL_PICKER_REQUEST = 120
    private var keyList: ArrayList<String>? = null
    var checkData = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        initView()
        initInstances()
        setListeners()
        //takeAutomaticMobileNo()
        //votecount()
    }
    private fun initView() {
        loginButton = findViewById(R.id.nextButton)
        userNameEditText = findViewById(R.id.userNameEditText)
        progressBar = findViewById(R.id.progreeBar)
        processText = findViewById(R.id.errorText)

    }


    private fun initInstances() {

        auth = FirebaseAuth.getInstance()
        mdatabase = FirebaseDatabase.getInstance()
        mDatabaseReference = mdatabase!!.getReference()

        // for change system navigation bar colour
        window.navigationBarColor = resources.getColor(R.color.colorPrimaryDark)
        builder = AlertDialog.Builder(this)

        mCallBacks = object : OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(phoneAuthCredential: PhoneAuthCredential) {
                println(">>>>>>>>>>>>>>>>> mobile no otp 2 " + "otp send")
                signIn(phoneAuthCredential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                processText!!.text = e.message
                processText!!.setTextColor(Color.RED)
                processText!!.visibility = View.VISIBLE
                println(">>>>>>>>>>>>>>>>> mobile no otp 3e " + "otp send fail ")
            }

            override fun onCodeSent(otpText: String, forceResendingToken: ForceResendingToken) {
                super.onCodeSent(otpText, forceResendingToken)

                //sometime the code is not detected automatically
                //so user has to manually enter the code
                println(">>>>>>>>>>>>>>>>> mobile no otp 4 " + "otp send succ ")
                processText!!.text = "OTP has been Sent"
                processText!!.visibility = View.VISIBLE
                Handler().postDelayed({
                    val otpIntent = Intent(this@Login2Activity, OtpActivity::class.java)
                    otpIntent.flags =
                        Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    otpIntent.putExtra("verificationId", otpText)
                    otpIntent.putExtra("phoneNo", userNameEditText!!.text.toString())
                    startActivity(otpIntent)
                }, 10000)
            }
        }
    }


    private fun setListeners() {
        loginButton!!.setOnClickListener(this)
    }



    override fun onClick(view: View?) {
        when (view!!.id) {
            R.id.nextButton -> {
                processText!!.text = " "
                if (isValid()) {
                    if (checkInternetConnection()) {
                        progressBar!!.visibility = View.VISIBLE
                        loginButton!!.visibility = View.GONE
                     //allowToRun();
                        Log.d("getmevalue_phone",userNameEditText!!.toString())
                        forCheckUserExistOrNot(userNameEditText!!.text.toString())


                    }
                }
            }
        }
    }

    private fun allowToRun() {
        val options = PhoneAuthOptions.newBuilder(auth!!)
            .setPhoneNumber("+91"+userNameEditText!!.text.toString())
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(this@Login2Activity)
            .setCallbacks(mCallBacks!!)
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }

    // check validation of edit view
    private fun isValid(): Boolean {
        return if (TextUtils.isEmpty(userNameEditText!!.text)) {
            userNameEditText!!.error = "Enter Mobile No."
            false
        } else {
            true
        }
    }

    fun checkInternetConnection(): Boolean {
        var connected = false
        val connectivityManager =
            applicationContext.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        connected =
            if (connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).state == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).state == NetworkInfo.State.CONNECTED
            ) {
                //we are connected to a network
                true
            } else {
                false
            }
        return connected
    }

    override fun onStart() {
        super.onStart()
        val user = auth!!.currentUser
        val userPhoneNumber=user!!.phoneNumber
        if (userPhoneNumber != null) {
            sendToMain(userPhoneNumber)
        }
    }

    private fun sendToMain(userDetail:String) {
        forCheckUserExistOrNot(userDetail)

    }

    private fun signIn(credential: PhoneAuthCredential) {
        auth!!.signInWithCredential(credential).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                sendToMain(auth!!.currentUser!!.phoneNumber!!)
            } else {
                processText!!.text = task.exception!!.message
                processText!!.setTextColor(Color.RED)
                processText!!.visibility = View.VISIBLE
            }
        }
    }

    fun createUserProfile(uniqueid:String){

    Log.d("getmevalue","createUserProfile")
        val users: MutableMap<String, String> = HashMap()
        users["uniqueid"] = uniqueid

        val saveUserUniqueId = "8275045452"


        mDatabaseReference!!.child("allusers").child(saveUserUniqueId).setValue(users,
            object : DatabaseReference.CompletionListener {
                override fun onComplete(error: DatabaseError?, ref: DatabaseReference) {
                    val users1: MutableMap<String, String> = HashMap()
                    users1["mobile"] = userNameEditText!!.text.toString()
                    mDatabaseReference!!.child("users").child(uniqueid).child("profile").setValue(users1,
                    object: DatabaseReference.CompletionListener{
                        override fun onComplete(error: DatabaseError?, ref: DatabaseReference) {
                            val mainIntent = Intent(this@Login2Activity, MainActivity::class.java)
                            startActivity(mainIntent)
                            finish()
                        }

                    })

                }

            })

    }


    fun forGetTimeFromFirebase(){
        Log.d("getmevalue","forGetTimeFromFirebase")
        FirebaseFunctions.getInstance().getHttpsCallable("stamp")
            .call().addOnSuccessListener(object : OnSuccessListener<HttpsCallableResult?> {
                override fun onSuccess(p0: HttpsCallableResult?) {
                    Log.d("getmevalue","HttpsCallableResult")
                    val timestamp = p0!!.getData()
                    if(auth!!.currentUser!!.phoneNumber!=null){
                        Log.d("getmevalue","forGetif")
                        Log.d("getmevalue",auth!!.currentUser!!.phoneNumber)
                        val saveUserUniqueId = auth!!.currentUser!!.phoneNumber+ timestamp
                        createUserProfile(saveUserUniqueId!!)
                    }else{
                        val saveUserUniqueId = "+91"+userNameEditText!!.text.toString() + timestamp
                        createUserProfile(saveUserUniqueId)
                    }
                }



            })
    }





    fun forCheckUserExistOrNot(userDetail: String){
        keyList = ArrayList()
        Log.d("getmevalue","go")
        Log.d("getmevalue",userDetail)

        //   Query myTopPostsQuery  = mDatabaseReference.child("8830512432");
        val myTopPostsQuery: Query = mDatabaseReference!!.child("allusers").child(userDetail)

        myTopPostsQuery.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d("getmevalue","inside1")
                Log.d("getmevalue",snapshot.toString())
                if (snapshot.value !=null) {
                        Log.d("getmevalue","1")
                        val mainIntent = Intent(this@Login2Activity, MainActivity::class.java)
                        startActivity(mainIntent)
                        finish()
                    } else {
                        Log.d("getmevalue","2")
                       forGetTimeFromFirebase()
                    }

            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

}