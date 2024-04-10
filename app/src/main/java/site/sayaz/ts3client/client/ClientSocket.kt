package site.sayaz.ts3client.client

import android.content.ContentValues.TAG
import android.util.Log
import com.github.manevolent.ts3j.event.TS3Listener
import com.github.manevolent.ts3j.identity.LocalIdentity
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket
import site.sayaz.ts3client.ui.server.LoginData
import site.sayaz.ts3client.util.base64Sha1
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.util.concurrent.TimeoutException


class ClientSocket(val loginData: LoginData, val identityDataDao: IdentityDataDao) {


    private val _client = LocalTeamspeakClientSocket()
    val client: LocalTeamspeakClientSocket
        get() = _client



    @Throws(TimeoutException::class)
    suspend fun connect(): LocalTeamspeakClientSocket {
        // Set up _client
        val listener: TS3Listener = object : TS3Listener {}
        val identity: LocalIdentity = getIdentity()

        _client.setIdentity(identity)
        _client.addListener(listener)
        _client.nickname = loginData.nickname
        _client.connect(
            loginData.hostname,
            base64Sha1(loginData.password),
            10000L
        )
        Log.d("ClientSocket", "Connected to ${loginData.hostname}")

        return _client
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
}
