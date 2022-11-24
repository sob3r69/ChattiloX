package com.sob3r.chattilo.twitch_chat

import MessageParser
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.sob3r.chattilo.R
import com.sob3r.chattilo.userdata.UserDataDB
import kotlinx.coroutines.runBlocking

class TwitchChat : AppCompatActivity() {

    val userDatabase by lazy { UserDataDB.getDatabase(this).userDataDao() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_twitch_chat)

        suspend fun getToken(): String {
            return userDatabase.getTokenFromDb()
        }

        val startParsingBtn: Button = findViewById(R.id.startParsingBtn)
        var accessToken: String

        startParsingBtn.setOnClickListener{
            runBlocking {
                accessToken = getToken()
            }
            Thread {
                println("NEW THREAD")
                MessageParser("user", "#sob3r__", accessToken).startParse()
            }.start()
        }

    }
}