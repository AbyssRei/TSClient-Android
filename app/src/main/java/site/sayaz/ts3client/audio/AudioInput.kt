package site.sayaz.ts3client.audio

import android.util.Log
import com.github.manevolent.ts3j.audio.Microphone
import com.github.manevolent.ts3j.enums.CodecType
import java.util.*


class AudioInput : Microphone {
    // Microphone interface
    private val packetQueue: Queue<ByteArray> = LinkedList()
    private var state = InputState.STOP
    private val AudioEncoder = AudioEncoder(48000, 1)

    fun start() {
        Log.d("AudioInput", "Starting audio input")
        state = InputState.START
    }
    fun write(buffer: ByteArray?) {
        AudioEncoder.encode(buffer?: ByteArray(0)) {
            packetQueue.add(it)
        }
    }

    override fun isMuted(): Boolean {
        if (state == InputState.MUTE) {
            Log.d("AudioInput", "Microphone is muted.")
            return true
        }
        return false
    }
    override fun isReady(): Boolean {
        return state != InputState.STOP
    }

    override fun getCodec(): CodecType {
        return CodecType.OPUS_MUSIC
    }

    override fun provide(): ByteArray {
        return try {
            if (packetQueue.peek() == null) {
                Log.d("AudioInput", "No packets available.")
                return ByteArray(0)
            }
            val packet = packetQueue.remove()
            if (packet == null){
                Log.d("AudioInput", "Packet is null.")
                return ByteArray(0)
            }
            // underflow
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