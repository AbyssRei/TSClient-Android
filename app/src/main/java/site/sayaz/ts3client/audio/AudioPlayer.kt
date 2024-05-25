package site.sayaz.ts3client.audio

import android.media.AudioAttributes
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.util.Log
import com.github.manevolent.ts3j.protocol.packet.PacketBody0Voice

class AudioPlayer {
    private var audioTrack: AudioTrack
    private val audioDecoder = AudioDecoder(48000, 1)
    private var outputState = OutputState.STOP

    init {
        val minBufferSize = AudioTrack.getMinBufferSize(
            48000,
            AudioFormat.CHANNEL_OUT_MONO,
            AudioFormat.ENCODING_PCM_16BIT
        )
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_MEDIA)
            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
            .build()
        val audioFormat = AudioFormat.Builder()
            .setSampleRate(48000)
            .setEncoding(AudioFormat.ENCODING_PCM_16BIT)
            .setChannelMask(AudioFormat.CHANNEL_OUT_MONO)
            .build()
        audioTrack = AudioTrack(
            audioAttributes,
            audioFormat,
            minBufferSize,
            AudioTrack.MODE_STREAM,
            AudioManager.AUDIO_SESSION_ID_GENERATE
        )
    }

    fun start() {
        audioTrack.play()
        outputState = OutputState.START
    }
    fun deafen() {
        outputState = OutputState.DEAFEN
    }
    fun undeafen() {
        outputState = OutputState.START
    }
    fun playPacket(voicePacket: PacketBody0Voice) {
            var audioData = voicePacket.codecData
            if (audioData == null) {
                Log.d("AudioPlayer", "No audio data")
                audioData = ByteArray(0)
            }
            if (outputState == OutputState.DEAFEN) return
            audioDecoder.decode(audioData) {
                audioTrack.write(it, 0, it.size)
            }
    }

    fun stop() {
        if (audioTrack.state != AudioTrack.STATE_UNINITIALIZED) {
            audioTrack.stop()
            outputState = OutputState.STOP
        }
    }
}
enum class OutputState {
    START,
    STOP,
    DEAFEN
}