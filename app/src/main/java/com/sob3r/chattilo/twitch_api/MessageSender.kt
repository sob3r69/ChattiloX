package com.sob3r.chattilo.twitch_api

import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*

class MessageSender(
    private val channel: String,
    private val accessToken: String) {

    private val serverAddress = "irc.chat.twitch.tv"
    private val port = 6667

    private val selectedChannel = this.channel

    private val token = "oauth:$accessToken"
    private lateinit var sendChannel: ByteWriteChannel
    private lateinit var receiveChannel: ByteReadChannel
    private lateinit var serverSocket: Socket


    suspend fun sendMessage() = coroutineScope{
        val selectorManager = SelectorManager(Dispatchers.IO)

        serverSocket = aSocket(selectorManager).tcp().connect(serverAddress, port)
        println("Server is listening at ${serverSocket.localAddress}")

        launch {
            receiveChannel = serverSocket.openReadChannel()
            sendChannel = serverSocket.openWriteChannel(autoFlush = true)

            try {
                loginToServer(token, "nickname")
                joinToChannel(selectedChannel)

                while (true) {
                    val serverMsg = receiveChannel.readUTF8Line()

                    println(serverMsg)
                    sendMsg("PRIVMSG $channel :убейте меня пж")
                    break
                }
            } catch (e: Throwable) {
                withContext(Dispatchers.IO) {
                    serverSocket.close()
                }
            }
        }
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

    private suspend fun sendMsg(msg: String) {
        sendChannel.writeStringUtf8(msg + "\n")
        println("<< Message sended")
    }
}