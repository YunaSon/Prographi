package org.prography.lemorning.src.view

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityCreateSongBinding
import org.prography.lemorning.src.viewmodel.CreateSongViewModel
import java.io.IOException
import java.util.*

class CreateSongActivity(override val layoutId: Int = R.layout.activity_create_song)
    : BaseActivity<ActivityCreateSongBinding, CreateSongViewModel>() {

    companion object {
        const val REQUEST_PERMISSION = 2
        const val REQUEST_CODE_CHOOSE = 3
        const val REQUEST_STORAGE_PERMISSION = 4
    }

    var mediaRecorder : MediaRecorder? = null
    var mediaPlayer : MediaPlayer? = null
    var tempFile = ""

    override fun getViewModel(): CreateSongViewModel = ViewModelProvider(this).get(CreateSongViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {

        binding.ivThumbnailCreateSong.setOnClickListener { openImagePicker() }

        binding.mbtnRecordCreateSong.setOnClickListener {
            if (viewmodel.isRecording == null || !viewmodel.isRecording!!) {
                tempFile = "${externalCacheDir?.absolutePath ?: cacheDir}/${Date().time}.mp3"
                mediaRecorder = MediaRecorder().apply {
                    setAudioSource(MediaRecorder.AudioSource.MIC)
                    setOutputFormat(MediaRecorder.OutputFormat.MPEG_4)
                    setOutputFile(tempFile)
                    setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
                    setMaxDuration(30000)
                    try {
                        prepare()
                    } catch (e : IOException) {
                        e.printStackTrace()
                    }
                    start()
                    binding.mbtnRecordCreateSong.text = "끝"
                    toggleTimerForAudio(this)
                    viewmodel.isRecording = true
                }
            } else {
                mediaRecorder?.let {
                    it.stop()
                    viewmodel.isRecording = false
                    binding.mbtnRecordCreateSong.text = "다시 녹음하기"
                    binding.mbtnPlayCreateSong.isEnabled = true
                }
            }
        }
        binding.mbtnPlayCreateSong.setOnClickListener {
            mediaRecorder?.let {
                mediaPlayer?.let {
                    if (it.isPlaying) {
                        it.pause()
                        binding.mbtnPlayCreateSong.text = "듣기"
                    }
                    else {
                        it.start()
                        binding.mbtnPlayCreateSong.text = "그만"
                    }
                } ?: startPlaying()
            }
        }

        binding.mbtnSubmitCreateSong.setOnClickListener {
            if (tempFile.isNotEmpty() && viewmodel.profileUri.value != null && binding.etTitleCreateSong.text.isNotBlank())
                viewmodel.postSong(binding.etTitleCreateSong.text.toString(), tempFile)
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.RECORD_AUDIO), REQUEST_PERMISSION)
        }

        /* Data Observing */
        viewmodel.canFinish.observe(this, androidx.lifecycle.Observer { it.get()?.let { if (it) finish() } })
        viewmodel.curAmpitude.observe(this, androidx.lifecycle.Observer { binding.arvCreateSong.update(it) })
    }

    private fun toggleTimerForAudio(mediaRecorder: MediaRecorder) {
        if (viewmodel.isRecording == null) {
            viewmodel.registerTimerOnAudio(mediaRecorder)
        }
        binding.arvCreateSong.recreate()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            var check = true
            for (i in grantResults) {
                check = check && i == PackageManager.PERMISSION_GRANTED
            }
            if (!check) {
                finish()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            REQUEST_CODE_CHOOSE -> if (resultCode == RESULT_OK) cropImage(data?.data)
            CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE -> {
                val result = CropImage.getActivityResult(data);
                if (resultCode == Activity.RESULT_OK) {
                    viewmodel.profileUri.value = result.uri
                } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                    result.error.printStackTrace()
                    showToast("이미지 등록에 실패하였습니다.")
                }
            }
        }
    }

    private fun openImagePicker() {
        if (!checkPermissions()) return
        startActivityForResult(Intent.createChooser(Intent().apply {
            type = "image/*"
            action = Intent.ACTION_GET_CONTENT
        }, "Select Picture"), REQUEST_CODE_CHOOSE)
    }

    private fun cropImage(uri : Uri?) {
        uri?.let {
            if (!checkPermissions()) return
            CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setAspectRatio(1, 1)
                .setCropShape(CropImageView.CropShape.OVAL)
                .start(this)
        }
    }

    fun startPlaying() {
        mediaPlayer = MediaPlayer().apply {
            try {
                setDataSource(tempFile)
                prepare()
                start()
            } catch (e : IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun checkPermissions() : Boolean {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                showToast("해당 기능을 사용하려면 카메라와 외부저장소 권한이 필요합니다.")
            } else {
                requestPermissions(arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_STORAGE_PERMISSION
                )
            }
            return false
        }
        return true
    }

    override fun onDestroy() {
        viewmodel.isRecording?.let { if (!it)  mediaRecorder?.release() }
        super.onDestroy()
    }
}