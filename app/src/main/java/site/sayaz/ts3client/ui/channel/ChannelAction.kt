package site.sayaz.ts3client.ui.channel

import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeMute
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.MicOff
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import site.sayaz.ts3client.ui.AppState

//TODO 断开当前连接
//TODO 打开消息页面
//TODO 开关麦
//TODO 扬声器开关

@Composable
fun ChannelAction(audioController: AudioController, appState: AppState) {
    when(appState.isInConnect){
        true -> {
            // open/close speaker
            when(appState.isDeafened){
                true -> IconButton(onClick = {
                    audioController.undeafen()
                }){
                    Icon(Icons.AutoMirrored.Filled.VolumeMute, contentDescription = "Open Speaker")
                }
                false -> IconButton(onClick = {
                    audioController.deafen()
                }){
                    Icon(Icons.AutoMirrored.Filled.VolumeUp, contentDescription = "Close Speaker")
                }
            }
            // open/close mic
            when(appState.isMuted){
                true -> IconButton(onClick = {
                    audioController.unmute()
                }) {
                    Icon(Icons.Default.MicOff, contentDescription = "Open Mic")
                }
                false -> IconButton(onClick = {
                    audioController.mute()
                    Log.d("ChannelAction", "Mute")
                }){
                    Icon(Icons.Default.Mic, contentDescription = "Close Mic")
                }
            }
            // more
            IconButton(onClick = {}){
                Icon(Icons.Default.MoreVert, contentDescription = "More")
            }
        }
        false -> {}
    }
}