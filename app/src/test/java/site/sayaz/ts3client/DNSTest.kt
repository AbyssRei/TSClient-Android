package site.sayaz.ts3client

import com.github.manevolent.ts3j.protocol.TS3DNS
import com.github.manevolent.ts3j.util.Ts3Debugging
import junit.framework.TestCase


class DNSTest : TestCase() {
    @Throws(Exception::class)
    fun testParser() {
        val dns = TS3DNS.lookup("voice.teamspeak.com")
        assertTrue(dns.size > 0)
        for (socketAddress in dns) Ts3Debugging.debug(socketAddress)
    }

    companion object {
        @Throws(Exception::class)
        @JvmStatic
        fun main(args: Array<String>) {
            DNSTest().testParser()
        }
    }
}