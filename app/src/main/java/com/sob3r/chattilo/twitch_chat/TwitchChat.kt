package com.sob3r.chattilo.twitch_chat

import com.sob3r.chattilo.twitch_api.MessageParser
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.sob3r.chattilo.R
import com.sob3r.chattilo.userdata.UserDataDB
import kotlinx.coroutines.*


class TwitchChat : AppCompatActivity() {

    private val userDatabase by lazy { UserDataDB.getDatabase(this).userDataDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitch_chat)

        val startParsingBtn: Button = findViewById(R.id.startParsingBtn)
        val closeParsingBtn: Button = findViewById(R.id.closeConnectBtn)

//      Придумать как куротин прервать или не куротин ну короче прервать надо придумать как
        startParsingBtn.setOnClickListener {
            startParse()
        }

        closeParsingBtn.setOnClickListener {
            stopParse()
        }

    }

    private fun startParse() = lifecycleScope.launch(Dispatchers.IO) {
        MessageParser("user", "#sob3r__", userDatabase.getTokenFromDb()).startParse()
    }


    private fun stopParse() = lifecycleScope.launch(Dispatchers.IO) {
        MessageParser("user", "#sob3r__", userDatabase.getTokenFromDb()).closeConnect()
    }

    private suspend fun getToken(): String {
        return userDatabase.getTokenFromDb()
    }

}