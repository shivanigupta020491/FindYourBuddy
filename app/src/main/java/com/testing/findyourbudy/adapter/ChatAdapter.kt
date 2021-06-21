package com.testing.findyourbudy.adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.testing.findyourbudy.R
import com.testing.findyourbudy.pojo.MessagePojo
import java.sql.Time
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ChatAdapter(mContext: Context, mMessageList: ArrayList<MessagePojo>):RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var view: View
    private val VIEW_TYPE_MESSAGE_SENT = 1
    private val VIEW_TYPE_MESSAGE_RECEIVED = 2
    private val context: Context = mContext
    var mMessageList: ArrayList<MessagePojo> = mMessageList
    var currentDate = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault()).format(Date())
    val currenTime = SimpleDateFormat("hh:mm", Locale.getDefault()).format(Date())


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            view = LayoutInflater.from(context).inflate(R.layout.item_message_sent, parent, false);
            return  SentMessageHolder(view);

        } else if (viewType == VIEW_TYPE_MESSAGE_RECEIVED) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_received, parent, false);
            return  ReceivedMessageHolder(view);
        }

        return null!!

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val message: MessagePojo = mMessageList[position] as MessagePojo

        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder).bind(message)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedMessageHolder).bind(message)
        }
    }

    override fun getItemCount(): Int {
       return mMessageList.size
    }


    private inner class ReceivedMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var messageText: TextView
        var timeText: TextView
        var nameText: TextView
       // var profileImage: ImageView

        init {
            messageText = itemView.findViewById(R.id.text_gchat_user_other)
            timeText = itemView.findViewById(R.id.text_gchat_timestamp_other)
            nameText = itemView.findViewById(R.id.text_gchat_message_other)
           // profileImage = itemView.findViewById(R.id.image_gchat_profile_other) as ImageView
        }

        fun bind(message: MessagePojo) {
            messageText.setText(message.message)

            // Format the stored timestamp into a readable String using method.
           // timeText.setText(Utils.formatDateTime(message.createdAt))
            timeText.setText(currentDate)
            nameText.setText(message.sender!!.nickname)

            print(">>>>>>>>>> currentDate " + currentDate)

            // Insert the profile image from the URL into the ImageView.
//            Utils.displayRoundImageFromUrl(
//                context,
//                message!!.sender!!.profileUrl,
//                profileImage
//            )

           // Glide.with(context).load(message!!.sender!!.profileUrl).into(profileImage);
        }


    }
    private inner class SentMessageHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var messageText: TextView
        var timeText: TextView

        init {
            messageText = itemView.findViewById(R.id.text_gchat_message_me) as TextView
            timeText = itemView.findViewById(R.id.text_gchat_timestamp_me) as TextView
        }
        fun bind(message: MessagePojo) {
            messageText.setText(message.message)

            // Format the stored timestamp into a readable String using method.
           // timeText.setText(Utils.formatDateTime(message.createdAt))
            timeText.setText(currentDate)
        }


    }



}