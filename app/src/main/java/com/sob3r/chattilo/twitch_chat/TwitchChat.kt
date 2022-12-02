package com.sob3r.chattilo.twitch_chat

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.sob3r.chattilo.R
import com.sob3r.chattilo.twitch_api.TwitchClient
import com.sob3r.chattilo.twitch_api.globalBoolean
import com.sob3r.chattilo.twitch_api.globalNick
import com.sob3r.chattilo.twitch_api.globalText
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

        val channelText: TextView = findViewById(R.id.textView)
        val sendMessageBtn: ImageButton = findViewById(R.id.sendMessageBTN)
        val editText: EditText = findViewById(R.id.editText)

        val chnlName = intent.getStringExtra(channelName)
        channelText.text = chnlName

        startParse(chnlName!!, twitchAdapter, twitchChat)

        sendMessageBtn.setOnClickListener {
            globalText = editText.text.toString()
            closeKeyboard()
            sendMsg()
            twitchAdapter.addMessage(TwitchMessageData(globalNick, globalText))
        }
    }

    private fun sendMsg() = lifecycleScope.launch(Dispatchers.IO){
        globalBoolean = true
    }

    private fun startParse(channel: String, adapter: TwitchAdapter, rv: RecyclerView) = lifecycleScope.launch(Dispatchers.IO) {
        val twitchApi = TwitchClient("user", channel, getToken(), adapter, rv)
        twitchApi.clientConnect()
    }

    private fun closeKeyboard(){
        val view: View? = this.currentFocus
        if (view != null){
            val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    private suspend fun getToken(): String {
        return userDatabase.getTokenFromDb()
    }
}