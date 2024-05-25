package site.sayaz.ts3client.audio

import android.util.Log
import com.github.manevolent.ts3j.audio.Microphone
import com.github.manevolent.ts3j.enums.CodecType
import java.util.*


class AudioInput : Microphone {
    // Microphone interface
    private val packetQueue: Queue<ByteArray> = LinkedList()
    var state = InputState.STOP
    private val audioEncoder = AudioEncoder(48000, 1)

    fun start() {
        Log.d("AudioInput", "Starting audio input")
        state = InputState.START
    }

    fun write(buffer: ByteArray?) {
        audioEncoder.encode(buffer?: ByteArray(0)) {
            packetQueue.add(it)
        }
    }

    fun clearQueue() {
        packetQueue.clear()
    }

    /**
     * 此接口在库中未实现
     * 不是 哥们
     */
    override fun isMuted(): Boolean {
        return false
    }
    override fun isReady(): Boolean {
        //Log.d("AudioInput", "Microphone is ready.")
        return state != InputState.STOP
    }

    override fun getCodec(): CodecType {
        return CodecType.OPUS_MUSIC
    }

    override fun provide(): ByteArray {
        if (state == InputState.MUTE) {
//            Log.d("AudioInput", "Muted.")
            return ByteArray(0)
        }
        return try {
            val packet = packetQueue.remove()
            //Log.d("AudioInput", "Providing packet.")
            packet
        } catch (ex: NoSuchElementException) {
            Log.d("AudioInput", "No packets available.")
            ByteArray(0) // signals the decoder on the clients to stop
        }
    }
}

enum class InputState {
    START,
    MUTE,
    STOP
}