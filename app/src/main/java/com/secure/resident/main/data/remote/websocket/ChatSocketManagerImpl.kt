package com.secure.resident.main.data.remote.websocket

import android.util.Log
import com.secure.resident.main.data.model.message.LiveMessageResponse
import com.secure.resident.main.domain.repository.ChatSocketManager
import kotlinx.serialization.json.Json
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatSocketManagerImpl @Inject constructor() : ChatSocketManager {

    private var webSocket: WebSocket? = null
    private var currentGroupId: String? = null
    private var onMessageCallback: ((LiveMessageResponse) -> Unit)? = null
    private var onErrorCallback: ((String) -> Unit)? = null

    private val client = OkHttpClient.Builder().build()

    private val json = Json {
        ignoreUnknownKeys = true
        isLenient = true
    }

    override fun connect(
        groupId: String,
        onMessageReceived: (LiveMessageResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        disconnect()

        currentGroupId = groupId
        onMessageCallback = onMessageReceived
        onErrorCallback = onError

        val request = Request.Builder()
            .url("ws://schnapps-statue-shallot.ngrok-free.dev/ws-chat")
            .build()

        Log.d("ChatSocket", "ws://schnapps-statue-shallot.ngrok-free.dev/ws-chat/ws-chat for group=$groupId")

        webSocket = client.newWebSocket(request, object : WebSocketListener() {

            override fun onOpen(webSocket: WebSocket, response: Response) {
                Log.d("ChatSocket", "WebSocket opened")

                val connectFrame = buildString {
                    append("CONNECT\n")
                    append("accept-version:1.2\n")
                    append("host:localhost\n")
                    append("\n")
                    append("\u0000")
                }

                Log.d("ChatSocket", "Sending CONNECT frame")
                webSocket.send(connectFrame)
            }

            override fun onMessage(webSocket: WebSocket, text: String) {
                Log.d("ChatSocket", "Received raw frame: $text")

                try {
                    when {
                        text.startsWith("CONNECTED") -> {
                            Log.d("ChatSocket", "STOMP CONNECTED received")

                            val group = currentGroupId ?: return

                            val subscribeFrame = buildString {
                                append("SUBSCRIBE\n")
                                append("id:sub-$group\n")
                                append("destination:/topic/groups/$group\n")
                                append("\n")
                                append("\u0000")
                            }

                            Log.d("ChatSocket", "Sending SUBSCRIBE to /topic/groups/$group")
                            webSocket.send(subscribeFrame)
                        }

                        text.startsWith("MESSAGE") -> {
                            val body = extractStompBody(text)
                            Log.d("ChatSocket", "Extracted body: $body")

                            if (body.isNotBlank()) {
                                val message = json.decodeFromString<LiveMessageResponse>(body)
                                Log.d("ChatSocket", "Parsed message id=${message.messageId}")
                                onMessageCallback?.invoke(message)
                            }
                        }

                        text.startsWith("RECEIPT") -> {
                            Log.d("ChatSocket", "RECEIPT frame received")
                        }

                        text.startsWith("ERROR") -> {
                            Log.e("ChatSocket", "STOMP ERROR frame: $text")
                            onErrorCallback?.invoke("STOMP error from server")
                        }

                        else -> {
                            Log.d("ChatSocket", "Unhandled frame: $text")
                        }
                    }
                } catch (e: Exception) {
                    Log.e("ChatSocket", "Parsing failed: ${e.message}", e)
                    onErrorCallback?.invoke(e.message ?: "Failed to parse socket message")
                }
            }

            override fun onMessage(webSocket: WebSocket, bytes: ByteString) {
                Log.d("ChatSocket", "Received bytes frame: ${bytes.utf8()}")
            }

            override fun onClosing(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("ChatSocket", "Closing socket: code=$code reason=$reason")
            }

            override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
                Log.d("ChatSocket", "Socket closed: code=$code reason=$reason")
            }

            override fun onFailure(webSocket: WebSocket, t: Throwable, response: Response?) {
                Log.e("ChatSocket", "Socket failure: ${t.message}", t)
                onErrorCallback?.invoke(t.message ?: "WebSocket connection failed")
            }
        })
    }

    override fun disconnect() {
        try {
            Log.d("ChatSocket", "Disconnecting socket")

            val disconnectFrame = buildString {
                append("DISCONNECT\n")
                append("\n")
                append("\u0000")
            }

            webSocket?.send(disconnectFrame)
            webSocket?.close(1000, "Disconnected")
            webSocket = null
            currentGroupId = null
            onMessageCallback = null
            onErrorCallback = null
        } catch (e: Exception) {
            Log.e("ChatSocket", "Disconnect error: ${e.message}", e)
        }
    }

    private fun extractStompBody(frame: String): String {
        val separatorIndex = frame.indexOf("\n\n")
        if (separatorIndex == -1) return ""

        return frame
            .substring(separatorIndex + 2)
            .removeSuffix("\u0000")
            .trim()
    }
}