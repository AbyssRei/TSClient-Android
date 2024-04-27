package site.sayaz.ts3client.audio

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

class AudioServiceConnection : ServiceConnection {
    var isBound: Boolean = false
    var audioService: AudioService? = null
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        audioService = (service as AudioService.AudioBinder).service
        isBound = true
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        audioService = null
        isBound = false
    }
}