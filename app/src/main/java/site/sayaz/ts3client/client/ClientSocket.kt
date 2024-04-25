package site.sayaz.ts3client.client

import android.content.ContentValues.TAG
import android.util.Log
import com.github.manevolent.ts3j.api.Channel
import com.github.manevolent.ts3j.api.VirtualServerInfo
import com.github.manevolent.ts3j.command.SingleCommand
import com.github.manevolent.ts3j.event.TS3Listener
import com.github.manevolent.ts3j.identity.LocalIdentity
import com.github.manevolent.ts3j.protocol.ProtocolRole
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket
import site.sayaz.ts3client.audio.AudioPlayer
import site.sayaz.ts3client.audio.AudioRecorder
import site.sayaz.ts3client.util.base64Sha1
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream


class ClientSocket(
    val serverData: ServerData,
    private val identityDataDao: IdentityDataDao,
    private val audioRecorder: AudioRecorder,
) {

    val channelList: Iterable<Channel> get() = _client.listChannels()
    private val _client = LocalTeamspeakClientSocket()
    val client: LocalTeamspeakClientSocket
        get() = _client

    val isConnected: Boolean
        get() = _client.isConnected

    @Throws(Exception::class)
    suspend fun connect(ts3Listener: TS3Listener): LocalTeamspeakClientSocket {
        // Set up _client
        val listener: TS3Listener = ts3Listener
        val identity: LocalIdentity = getIdentity()
        val audioPlayer = AudioPlayer()

        _client.setIdentity(identity)
        _client.addListener(listener)
        _client.nickname = serverData.nickname
        _client.microphone = audioRecorder.getAudioInput()
        _client.setVoiceHandler { audioPlayer.playPacket(it)}
        _client.connect(
            serverData.hostname,
            base64Sha1(serverData.password),
            10000L
        )
        _client.subscribeAll()

        //audio
        audioRecorder.start()
        audioPlayer.start()

        Log.d("ClientSocket", "Connected to ${serverData.hostname}")
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

    fun getServerInfo() : VirtualServerInfo{
        if (!_client.isConnected) throw Exception("Not connected to server")
        try {
            return _client.executeCommand(
                SingleCommand("serverinfo", ProtocolRole.CLIENT)
            ) .get().toList().first().parameters as VirtualServerInfo
        }catch (e: Exception) {
            throw Exception("Failed to get server info")
        }
    }



    fun disconnect() {
        _client.disconnect()
        audioRecorder.stop()
    }
}
