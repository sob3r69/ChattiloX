package com.sob3r.chattilo.startscreen

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.sob3r.chattilo.R
import com.sob3r.chattilo.auth.AuthActivity
import com.sob3r.chattilo.userdata.UserDataDB

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val openLinkButton: Button = findViewById(R.id.openAuthLink)
        val openTwitchChat: Button = findViewById(R.id.twitchButton)

        openLinkButton.setOnClickListener{
            val authActivity = Intent(this, AuthActivity::class.java)
            startActivity(authActivity)
        }


        openTwitchChat.setOnClickListener{
            DialogManager(this).searchChannelDialog()
        }
    }
}