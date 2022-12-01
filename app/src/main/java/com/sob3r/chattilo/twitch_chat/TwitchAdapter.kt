package com.sob3r.chattilo.twitch_chat

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.sob3r.chattilo.R
import com.sob3r.chattilo.databinding.TwitchMessageboxBinding

class TwitchAdapter: RecyclerView.Adapter<TwitchAdapter.MessageHolder>(){
    private val messageList = ArrayList<TwitchMessageData>()

    class MessageHolder(item: View): RecyclerView.ViewHolder(item) {
        private val binding = TwitchMessageboxBinding.bind(item)

        fun bind(messageData: TwitchMessageData) = with(binding){
            userNickText.text = messageData.userName
            userMessageText.text = messageData.userMessage
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.twitch_messagebox, parent, false)
        return MessageHolder(view)
    }

    override fun onBindViewHolder(holder: MessageHolder, position: Int) {
        holder.bind(messageList[position])
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addMessage(message: TwitchMessageData){
        messageList.add(message)
        notifyDataSetChanged()
    }
}