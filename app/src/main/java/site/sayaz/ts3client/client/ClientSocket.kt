package site.sayaz.ts3client.client

import android.content.ContentValues.TAG
import android.content.Context
import android.media.MediaRecorder
import android.util.Log
import com.github.manevolent.ts3j.api.Channel
import com.github.manevolent.ts3j.audio.Microphone
import com.github.manevolent.ts3j.event.TS3Listener
import com.github.manevolent.ts3j.identity.LocalIdentity
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket
import site.sayaz.ts3client.audio.AudioInput
import site.sayaz.ts3client.audio.AudioRecorder
import site.sayaz.ts3client.ui.server.LoginData
import site.sayaz.ts3client.util.base64Sha1
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeoutException


class ClientSocket(
    private val loginData: LoginData,
    private val identityDataDao: IdentityDataDao,
    private val audioRecorder: AudioRecorder
) {


    private val _client = LocalTeamspeakClientSocket()
    val client: LocalTeamspeakClientSocket
        get() = _client

    @Throws(TimeoutException::class)
    suspend fun connect(ts3Listener: TS3Listener): LocalTeamspeakClientSocket {
        // Set up _client
        val listener: TS3Listener = ts3Listener
        val identity: LocalIdentity = getIdentity()
        //audio
        audioRecorder.start()

        _client.setIdentity(identity)
        _client.addListener(listener)
        _client.nickname = loginData.nickname
        _client.microphone = audioRecorder.getAudioInput()
        _client.connect(
            loginData.hostname,
            base64Sha1(loginData.password),
            10000L
        )
        _client.subscribeAll()
        Log.d("ClientSocket", "Connected to ${loginData.hostname}")
        return _client
    }

    @Throws(Exception::class)
    fun switchChannel(channelID: Int,password: String = ""){
        _client.joinChannel(channelID,password)
    }



    private suspend fun getIdentity(): LocalIdentity {
        val identityData = identityDataDao.getIdentity()
        return if (identityData != null) {
            val inputStream = ByteArrayInputStream(identityData)
            val identity = LocalIdentity.read(inputStream)
            Log.d(TAG, "Loaded identity from database")
            identity
        } else {
            val outputStream = ByteArrayOutputStream()
            val newIdentity = LocalIdentity.generateNew(15)
            newIdentity.save(outputStream)
            identityDataDao.insert(IdentityData(0, outputStream.toByteArray()))
            Log.d(TAG, "Generated new identity")
            newIdentity
        }
    }

    fun disconnect() {
        _client.disconnect()
        audioRecorder.stop()
    }
}
