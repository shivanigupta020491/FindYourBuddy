package com.testing.findyourbudy.activity

import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.testing.findyourbudy.R
import com.testing.findyourbudy.RetrofitClient
import com.testing.findyourbudy.adapter.ChatAdapter
import com.testing.findyourbudy.data.ChatResponse
import com.testing.findyourbudy.pojo.MessagePojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ChatboxActivity : AppCompatActivity() {

    private var mMessageRecycler: RecyclerView? = null
    private var mMessageEditText: EditText? = null
    private var mMessageSendBtn: ImageButton? = null
    private var mMessageAdapter: ChatAdapter? = null
    private var messageList:ArrayList<MessagePojo> = ArrayList()
    var firebase: FirebaseDatabase? = null
    var databaseRef: DatabaseReference? = null
   // var firebaseAuth: FirebaseAuth? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbox)

        print(">>>> chat box response ")
        initView()
        initInstance()
        setListeneres()
       // setAdapter()

       sendMessage()
        getMessageData()

//        GlobalScope.launch(Dispatchers.IO) {
//            hitApi()
//        }

    }

    private fun sendMessage() {
        // Write a message to the database
        // Write a message to the database
        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("message")

        myRef.setValue("Hello, World!")
    }

    private fun initView() {

        mMessageRecycler = findViewById(R.id.recycler_gchat)
        mMessageEditText = findViewById(R.id.edit_gchat_message)
        mMessageSendBtn = findViewById(R.id.button_gchat_send)
        messageList = ArrayList()
    }

    private fun initInstance() {
        print(">>>> chat box response 11")

     firebase = FirebaseDatabase.getInstance()
     //firebaseAuth = FirebaseAuth.getInstance()
     databaseRef = firebase!!.reference


    }

    private fun setListeneres() {

    }

    fun setAdapter(){



    }

    fun hitApi() {
//        var service = retrofit.create(ApiService::class.java)
//        val call = service.getMessageForChatbox()
        var request = RetrofitClient.getApi().getMessageForChatbox()


        request.enqueue(object : Callback<List<ChatResponse>> {
            override fun onResponse(
                call: Call<List<ChatResponse>>?,
                response: Response<List<ChatResponse>>?
            ) {

                var response = response!!.body()
                print(">>>> chat box response ")
            }

            override fun onFailure(call: Call<List<ChatResponse>>?, t: Throwable?) {
                print(">>>. fail")
            }

        })
        }

    fun getMessageData(){
       Log.d(">>>>>>> data message ", "message")
        databaseRef!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                Log.d(">>>>>>> data value", "BB")
                val value = snapshot.child("Chats").child("ankush").getValue().toString()

//                var gson = Gson()
//                var messagePojo = gson.fromJson((snapshot.value.toString()),MessagePojo::class.java)
//                messageList.add(messagePojo)

//                Log.d(">>>>>>> data message ",snapshot.value.toString())
//                Log.d(">>> data messagelist ", messageList.toString())

                Log.d(">>>>>>> data value", value)

//                mMessageAdapter = ChatAdapter(this@ChatboxActivity, messageList)
//                mMessageRecycler!!.layoutManager = LinearLayoutManager(this@ChatboxActivity)


                val listName = messageList

            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@ChatboxActivity, "Fail to get data.", Toast.LENGTH_SHORT)
                    .show();
            }

        })
    }


}