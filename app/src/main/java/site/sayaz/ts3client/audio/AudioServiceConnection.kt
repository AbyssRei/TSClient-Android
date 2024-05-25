package site.sayaz.ts3client.audio

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder

class AudioServiceConnection : ServiceConnection {
    var isBound: Boolean = false
    lateinit var audioService: AudioService
    override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
        audioService = (service as AudioService.AudioBinder).service
        isBound = true
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        isBound = false
    }
}