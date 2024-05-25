package site.sayaz.ts3client

import com.github.manevolent.ts3j.event.TS3Listener
import com.github.manevolent.ts3j.identity.LocalIdentity
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket
import org.junit.Test
import site.sayaz.ts3client.util.base64Sha1

class ConnectionTest {
    @Test
    fun testConnection() {
        // Test connection to a server
        val client = LocalTeamspeakClientSocket()
        val listener: TS3Listener = object : TS3Listener {}
        val identity: LocalIdentity = LocalIdentity.generateNew(10)

        println("Identity: $identity")
        client.setIdentity(identity)
        client.addListener(listener)
        client.nickname = "foo"
        client.connect(
            "voice.teamspeak.com",
            base64Sha1(""),
            10000L
        )
        assert(client.isConnected)
        client.disconnect()
    }

    @Test
    fun getChannelListTest(){
        val client = LocalTeamspeakClientSocket()
        val listener: TS3Listener = object : TS3Listener {}
        val identity: LocalIdentity = LocalIdentity.generateNew(10)

        println("Identity: $identity")
        client.setIdentity(identity)
        client.addListener(listener)
        client.nickname = "foo"
        client.connect(
            "voice.teamspeak.com",
            base64Sha1(""),
            10000L
        )
        assert(client.isConnected)
        val channelList = client.listChannels()
        val clientList = client.listClients()
        client.disconnect()
    }
    @Test
    fun canTalkTest(){
        val client = LocalTeamspeakClientSocket()
        val listener: TS3Listener = object : TS3Listener {}
        val identity: LocalIdentity = LocalIdentity.generateNew(10)

        println("Identity: $identity")
        client.setIdentity(identity)
        client.addListener(listener)
        client.nickname = "joeekk"
        client.connect(
            "s",
            base64Sha1(""),
            10000L
        )
        assert(client.isConnected)
        val clientList = client.listClients()
        val listinfo = clientList.map { c -> client.getClientInfo(c.id) }
        client.disconnect()

    }
}