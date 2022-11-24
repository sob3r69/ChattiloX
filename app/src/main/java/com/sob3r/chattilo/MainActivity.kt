package com.sob3r.chattilo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.sob3r.chattilo.auth.AuthActivity
import com.sob3r.chattilo.twitch_chat.TwitchChat
import com.sob3r.chattilo.userdata.UserDataDB
import kotlinx.coroutines.runBlocking

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        val userDatabase by lazy { UserDataDB.getDatabase(this).userDataDao() }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val openLinkButton: Button = findViewById(R.id.openAuthLink)
        val testButton: Button = findViewById(R.id.testButton)
        val openTwitchChat: Button = findViewById(R.id.twitchButton)

        openLinkButton.setOnClickListener{
            val authActivity = Intent(this, AuthActivity::class.java)
            startActivity(authActivity)
        }

        testButton.setOnClickListener{
            runBlocking {
                println("<<<< ${userDatabase.getAll()}")
            }
        }

        openTwitchChat.setOnClickListener{
            val twitchChatActivity = Intent(this, TwitchChat::class.java)
            startActivity(twitchChatActivity)
        }

    }
}