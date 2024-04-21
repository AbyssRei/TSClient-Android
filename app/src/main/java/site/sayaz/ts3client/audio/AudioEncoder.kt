package site.sayaz.ts3client.audio

import android.media.MediaCodec
import android.media.MediaCodecInfo
import android.media.MediaFormat
import java.nio.ByteBuffer

class AudioEncoder(private val sampleRate: Int, private val channelCount: Int) {
    private val codec: MediaCodec
    private val bufferInfo = MediaCodec.BufferInfo()
    private var presentationTimeUs: Long = 0

    init {
        val format = MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_OPUS, sampleRate, channelCount)
        format.setInteger(MediaFormat.KEY_BIT_RATE, 64000)
        codec = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_AUDIO_OPUS)
        codec.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE)
        codec.start()
    }

    fun encode(input: ByteArray, output: (ByteArray) -> Unit) {
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