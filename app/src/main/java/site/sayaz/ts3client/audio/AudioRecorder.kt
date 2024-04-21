package site.sayaz.ts3client.audio

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.pm.PackageManager
import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import androidx.core.app.ActivityCompat
import com.github.manevolent.ts3j.audio.Microphone
import com.hjq.permissions.XXPermissions
import site.sayaz.ts3client.audio.AudioInput
import site.sayaz.ts3client.ui.util.toast
import java.lang.Exception

class AudioRecorder(val context: Context,private val audioInput: AudioInput) {
    private var isRecording = false
    private lateinit var audioRecord: AudioRecord
    private val sampleRate = 48000
    private val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private val audioFormat = AudioFormat.ENCODING_PCM_16BIT
    private val bufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)

    fun start() {
        try {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.RECORD_AUDIO
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                toast(context, "Please grant audio permission")
                XXPermissions.startPermissionActivity(context)
                return
            }

            audioRecord = AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, channelConfig, audioFormat, bufferSize)
            val buffer = ByteArray(bufferSize)
            audioRecord.startRecording()
            isRecording = true

            Thread {
                audioInput.start()
                while (isRecording) {
                    val readSize = audioRecord.read(buffer, 0, bufferSize)
                    if (readSize > 0) {
                        val audioData = buffer.copyOfRange(0, readSize)
                        Log.d("AudioRecorder", "Audio data size: ${audioData.size}")
                        audioInput.write(audioData)

                    }
                }
            }.start()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun stop() {
        isRecording = false
        audioRecord.stop()
        audioRecord.release()
    }

    fun getAudioInput(): Microphone {
        return audioInput
    }


}