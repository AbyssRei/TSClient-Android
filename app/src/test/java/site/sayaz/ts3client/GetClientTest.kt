package site.sayaz.ts3client

import com.github.manevolent.ts3j.event.TS3Listener
import com.github.manevolent.ts3j.identity.LocalIdentity
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket
import org.junit.After
import org.junit.Before
import org.junit.Test
import site.sayaz.ts3client.util.base64Sha1

class GetClientTest {
    private val client = LocalTeamspeakClientSocket()
    private val listener: TS3Listener = object : TS3Listener {}
    private val identity: LocalIdentity = LocalIdentity.generateNew(10)
    @Test
    fun getClient() {
        println("---------------Connecting to server-------------")
        println("Identity: $identity")
        client.setIdentity(identity)
        client.addListener(listener)
        client.nickname = "foo"
        client.connect(
            "",
            base64Sha1(""),
            10000L
        )
        assert(client.isConnected)


        val clientData = client.listClients()
//NOT WORK val clientChannel = client.listChannelClients(clientData.toList().first().channelId)
        println("Client Data: $clientData")


        println("---------------Disconnecting from server-------------")
        client.disconnect()
        assert(!client.isConnected)
    }
}