package com.sob3r.chattilo.twitch_api

import androidx.recyclerview.widget.RecyclerView
import com.sob3r.chattilo.twitch_chat.TwitchAdapter
import com.sob3r.chattilo.twitch_chat.TwitchChat
import com.sob3r.chattilo.twitch_chat.TwitchMessageData
import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*

class MessageParser(
    private val userNickname: String,
    private val channel: String,
    private val accessToken: String,
    private val adapter: TwitchAdapter,
    private val rv: RecyclerView
    ) {

    private val serverAddress = "irc.chat.twitch.tv"
    private val port = 6667

    private val nickname = this.userNickname
    private val selectedChannel = this.channel

    private val token = "oauth:$accessToken"
    private lateinit var sendChannel: ByteWriteChannel
    private lateinit var receiveChannel: ByteReadChannel
    private lateinit var serverSocket: Socket
    private val tAdapter = this.adapter

    suspend fun startParse() = coroutineScope {
        val selectorManager = SelectorManager(Dispatchers.IO)

        serverSocket = aSocket(selectorManager).tcp().connect(serverAddress, port)
        println("Server is listening at ${serverSocket.localAddress}")

        launch{
            receiveChannel = serverSocket.openReadChannel()
            sendChannel = serverSocket.openWriteChannel(autoFlush = true)
            try {
                loginToServer(token, nickname)
                joinToChannel(selectedChannel)

                while (true) {
                    val serverMsg = receiveChannel.readUTF8Line()

                    if (serverMsg!!.contains("PING")) {
                        sendMsg("PONG")

                    } else if (serverMsg.contains("PRIVMSG")) {
                        addUserMsg(getViewerNick(serverMsg), getViewerMsg(serverMsg))
                    }

                    delay(200)

                }
            } catch (e: Throwable) {
                withContext(Dispatchers.IO) {
                    serverSocket.close()
                }
            }
        }
    }

    suspend fun sendHelloMsg() = withContext(Dispatchers.IO){
        sendMsg("PRIVMSG $channel :Hello <3")
    }

    private suspend fun addUserMsg(nick: String, msg: String){
        withContext(Dispatchers.Main){
            rv.scrollToPosition(tAdapter.itemCount - 1)
            tAdapter.addMessage(TwitchMessageData(nick, msg))
        }
    }

    private fun getViewerNick(srcMessage: String): String {
        var isMessage = false
        var viewerNickname = ""

        if (srcMessage.contains("PRIVMSG")){
            isMessage = true
        }
        if (isMessage){
            val endIndex = srcMessage.indexOf('!')

            viewerNickname = srcMessage.substring(1, endIndex)
        }

        return "$viewerNickname:"

    }

    private fun getViewerMsg(srcMessage: String): String{
        var isMessage = false
        var viewerMsg = ""

        if (srcMessage.contains("PRIVMSG")){
            isMessage = true
        }
        if (isMessage){
            val startIndex = srcMessage.indexOf(string = " :")

            viewerMsg = srcMessage.substring(startIndex + 2)
        }

        return viewerMsg
    }

    private suspend fun loginToServer(pass: String, nick: String){
        sendMsg("PASS $pass")
        sendMsg("NICK $nick")
        println("<< Login message was send")
    }

    private suspend fun joinToChannel(channel: String){
        sendMsg("JOIN $channel")
        println("<< Join message was send")
    }

    private suspend fun sendMsg(msg: String){
        sendChannel.writeStringUtf8(msg + "\n")
    }
}