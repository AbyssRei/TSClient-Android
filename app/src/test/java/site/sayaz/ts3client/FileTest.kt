package site.sayaz.ts3client

import com.github.manevolent.ts3j.api.ChannelInfo
import com.github.manevolent.ts3j.api.VirtualServerInfo
import com.github.manevolent.ts3j.command.SingleCommand
import com.github.manevolent.ts3j.command.parameter.CommandSingleParameter
import com.github.manevolent.ts3j.event.TS3Listener
import com.github.manevolent.ts3j.identity.LocalIdentity
import com.github.manevolent.ts3j.protocol.ProtocolRole
import com.github.manevolent.ts3j.protocol.socket.client.LocalTeamspeakClientSocket
import org.junit.Test
import site.sayaz.ts3client.util.base64Sha1
import java.net.Socket

class FileTest {
    // API不支持
    @Test
    fun getIcon(){
        println("-------------------------------------------------------------------------------")
        // Test getting icon id
        val client = LocalTeamspeakClientSocket()
        val listener: TS3Listener = object : TS3Listener {}
        val identity: LocalIdentity = LocalIdentity.generateNew(10)

        println("Identity: $identity")
        client.setIdentity(identity)
        client.addListener(listener)
        client.nickname = "foo"
        client.connect(
            "global.teamspeak.app",
            base64Sha1(""),
            10000L
        )
        val info = VirtualServerInfo(
            client.executeCommand(
                SingleCommand("serverinfo", ProtocolRole.CLIENT)
            ).get().toList().first().parameters.associate {
                it.name to it.value
            }
        )

        // get channel icon id
        val channel = client.listChannels().toList()[0]

        val m2 = mutableMapOf<String, String>()
        val command = SingleCommand(
            "channelinfo", ProtocolRole.CLIENT
        )
        command.add(CommandSingleParameter("cid", "${channel.id}"))
        client.executeCommand(command).get().toList()[0].parameters.forEach{
            if (it.name != null && it.value != null)
                m2[it.name] = it.value
        }
        val channelInfo = ChannelInfo(channel.id,m2.toMap())


        // 初始化文件下载
        val dowCommand =  SingleCommand(
            "ftinitdownload", ProtocolRole.CLIENT)
        dowCommand.add( CommandSingleParameter("clientftfid", "0"))
        dowCommand.add( CommandSingleParameter("name", "/icon_${channelInfo.iconId}"))
        dowCommand.add( CommandSingleParameter("cid", "${channel.id}"))
        dowCommand.add( CommandSingleParameter("cpw", ""))
        dowCommand.add( CommandSingleParameter("seekpos", "0"))
        dowCommand.add( CommandSingleParameter("proto", "0"))
        val initDownload = client.executeCommand(
            dowCommand
        ).get().toList()


        // 获取文件下载的端口和密码
        val port = 90009
        val ftkey = ""

        // 创建一个新的 Socket 连接到文件下载的端口
        val socket = Socket("", port)

        // 发送文件下载的请求
        val outputStream = socket.getOutputStream()
        val request = "clientftfid=0 clientftfcid=0 ftkey=$ftkey size=0 seekpos=0 proto=1\n"
        outputStream.write(request.toByteArray())
        outputStream.flush()

        // 读取文件的内容
        val inputStream = socket.getInputStream()
        val icon = inputStream.readBytes()

        // 关闭 Socket
        socket.close()




        client.disconnect()

    }
}