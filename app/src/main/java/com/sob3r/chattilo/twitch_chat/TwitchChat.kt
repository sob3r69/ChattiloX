package com.sob3r.chattilo.twitch_chat

import android.app.Activity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.sob3r.chattilo.R
import com.sob3r.chattilo.twitch_api.MessageParser
import com.sob3r.chattilo.userdata.UserDataDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class TwitchChat : AppCompatActivity() {

    private val userDatabase by lazy { UserDataDB.getDatabase(this).userDataDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitch_chat)

        val twitchAdapter = TwitchAdapter()
        val twitchChat: RecyclerView = findViewById(R.id.twitchMessageRV)
        twitchChat.adapter = twitchAdapter

        val closeParsingBtn: Button = findViewById(R.id.closeConnectBtn)

        startParse(twitchAdapter, twitchChat)

        closeParsingBtn.setOnClickListener {
            stopParse(this)
        }
    }

    private fun startParse(adapter: TwitchAdapter, rv: RecyclerView) = lifecycleScope.launch(Dispatchers.IO) {
        MessageParser("user", "#xqc", getToken(), adapter, rv).startParse()
    }


    private fun stopParse(activity: Activity){
        activity.finish()
    }

    private suspend fun getToken(): String {
        return userDatabase.getTokenFromDb()
    }
}