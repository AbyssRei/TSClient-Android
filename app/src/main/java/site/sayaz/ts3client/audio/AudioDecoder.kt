package site.sayaz.ts3client.audio

import android.media.MediaCodec
import android.media.MediaFormat
import java.nio.ByteBuffer




class AudioDecoder (private val sampleRate: Int, private val channelCount: Int){
    private val codec: MediaCodec
    private val bufferInfo = MediaCodec.BufferInfo()
    private var presentationTimeUs: Long = 0

    init {
        val format = MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_OPUS, sampleRate, channelCount)
        format.setInteger(MediaFormat.KEY_BIT_RATE, 64000)
        val csd0bytes = byteArrayOf(
            0x4f, 0x70, 0x75, 0x73,  // Opus
            0x48, 0x65, 0x61, 0x64,  // Head
            0x01, // Version
            0x02,   // Channel Count
            0x00, 0x00, // Pre skip
            0x00.toByte(), 0xfa.toByte(), 0x00, 0x00,// Input Sample Rate (Hz), eg: 48000
            0x00, 0x00,  // Output Gain (Q7.8 in dB)
            0x00// Mapping Family
        )
        val csd1bytes = byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
        val csd2bytes = byteArrayOf(0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00)
        val csd0 = ByteBuffer.wrap(csd0bytes)
        format.setByteBuffer("csd-0", csd0)
        val csd1 = ByteBuffer.wrap(csd1bytes)
        format.setByteBuffer("csd-1", csd1)
        val csd2 = ByteBuffer.wrap(csd2bytes)
        format.setByteBuffer("csd-2", csd2)
        codec = MediaCodec.createDecoderByType(MediaFormat.MIMETYPE_AUDIO_OPUS)
        codec.configure(format, null, null, 0)
        codec.start()
    }

    fun decode(input: ByteArray, output: (ByteArray) -> Unit) {
        val inputBufferIndex = codec.dequeueInputBuffer(10000)
        if (inputBufferIndex >= 0) {
            val inputBuffer = codec.getInputBuffer(inputBufferIndex)
            inputBuffer?.clear()
            inputBuffer?.put(input)
            codec.queueInputBuffer(inputBufferIndex, 0, input.size, presentationTimeUs, 0)
            presentationTimeUs += 1000000L * input.size / 2 / sampleRate
        }

        var outputBufferIndex = codec.dequeueOutputBuffer(bufferInfo, 10000)
        while (outputBufferIndex >= 0) {
            val outputBuffer = codec.getOutputBuffer(outputBufferIndex)
            val outData = ByteArray(bufferInfo.size)
            outputBuffer?.get(outData)
            output(outData)
            codec.releaseOutputBuffer(outputBufferIndex, false)
            outputBufferIndex = codec.dequeueOutputBuffer(bufferInfo, 0)
        }
    }

    fun release() {
        codec.stop()
        codec.release()
    }
}