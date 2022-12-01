package com.sob3r.chattilo.twitch_chat

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.sob3r.chattilo.R
import com.sob3r.chattilo.twitch_api.MessageParser
import com.sob3r.chattilo.twitch_api.MessageSender
import com.sob3r.chattilo.userdata.UserDataDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TwitchChat : AppCompatActivity() {

    companion object {
        const val channelName: String = ""
    }

    private val userDatabase by lazy { UserDataDB.getDatabase(this).userDataDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitch_chat)

        val twitchAdapter = TwitchAdapter()
        val twitchChat: RecyclerView = findViewById(R.id.twitchMessageRV)
        twitchChat.adapter = twitchAdapter

        val closeParsingBtn: Button = findViewById(R.id.closeConnectBtn)
        val sendMessageBtn: Button = findViewById(R.id.sendMessageBTN)

        val channelName = intent.getStringExtra(channelName)

        startParse(channelName!!, twitchAdapter, twitchChat)

        sendMessageBtn.setOnClickListener {
//            sendMsg(channelName)
        }

        closeParsingBtn.setOnClickListener {
            stopParse(this)
        }
    }

    private fun sendMsg(channel: String) = lifecycleScope.launch(Dispatchers.IO){
        MessageSender(channelName, getToken()).sendMessage()
    }

    private fun startParse(channel: String, adapter: TwitchAdapter, rv: RecyclerView) = lifecycleScope.launch(Dispatchers.IO) {
        MessageParser("user", channel, getToken(), adapter, rv).startParse()
    }


    private fun stopParse(activity: Activity){
        activity.finish()
    }

    private suspend fun getToken(): String {
        return userDatabase.getTokenFromDb()
    }
}