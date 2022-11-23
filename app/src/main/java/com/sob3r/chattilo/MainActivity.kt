package com.sob3r.chattilo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.sob3r.chattilo.auth.AuthActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val openLinkButton = findViewById<Button>(R.id.openAuthLink)
        openLinkButton.setOnClickListener{
            val authActivity = Intent(this, AuthActivity::class.java)
            startActivity(authActivity)
        }

    }
}