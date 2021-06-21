package com.testing.findyourbudy.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.testing.findyourbudy.R

class MainActivity : AppCompatActivity() {

    var userName:TextView? = null
    var hobbiesBtn:TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initView()
        setListeners()
    }

    private fun initView() {

        userName = findViewById(R.id.userNameMain)
        hobbiesBtn = findViewById(R.id.setHobbiesButton)
    }

    private fun setListeners() {
        hobbiesBtn!!.setOnClickListener {
            val intent = Intent(this@MainActivity, SetHobbiesActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
}