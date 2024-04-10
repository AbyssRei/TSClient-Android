package site.sayaz.ts3client.audio

import com.github.manevolent.ts3j.audio.Microphone
import com.github.manevolent.ts3j.enums.CodecType
//
//
//class AudioInput: Microphone {
//    // Microphone interface
//    fun write(buffer: FloatArray?, len: Int) {
//        val opusPacket: ByteArray = doOpusEncodingHere(buffer, len)
//        packetQueue.add(opusPacket)
//    }
//
//    override fun isReady(): Boolean {
//        return true
//    }
//
//    override fun getCodec(): CodecType {
//        return CodecType.OPUS_MUSIC
//    }
//
//    override fun provide(): ByteArray {
//        return try {
//            if (packetQueue.peek() == null) return ByteArray(0) // underflow
//            val packet: OpusPacket = packetQueue.remove() ?: return ByteArray(0)
//            // underflow
//            packet.getBytes()
//        } catch (ex: NoSuchElementException) {
//            ByteArray(0) // signals the decoder on the clients to stop
//        }
//    }
//}