package com.testing.findyourbudy.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.testing.findyourbudy.R
import com.testing.findyourbudy.adapter.ChatAdapter
import com.testing.findyourbudy.pojo.MessagePojo

class ChatActivity : AppCompatActivity() {

    private var mMessageRecycler: RecyclerView? = null
    private var mMessageEditText: EditText? = null
    private var mMessageSendBtn: ImageButton? = null
    private var mMessageAdapter: ChatAdapter? = null
    private var messageList:ArrayList<MessagePojo>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        print(">>>> chat box response ")
    }
}