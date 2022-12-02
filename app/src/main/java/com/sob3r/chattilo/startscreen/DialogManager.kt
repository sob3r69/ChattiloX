package com.sob3r.chattilo.startscreen

import android.content.Intent
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.sob3r.chattilo.twitch_chat.TwitchChat

class DialogManager(val context: AppCompatActivity) {
    fun searchChannelDialog() {
        val channelField = EditText(context)
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Channel")
        builder.setMessage("Type in channel name")
        builder.setView(channelField)

        builder.setPositiveButton("OK"){_, _ ->
            val twitchChatActivity = Intent(context, TwitchChat::class.java)
            twitchChatActivity.putExtra(TwitchChat.channelName, "#${channelField.text}")
            startActivity(context, twitchChatActivity, null)
        }

        val dialog = builder.create()
        dialog.show()
    }

}