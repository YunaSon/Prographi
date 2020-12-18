package org.prography.lemorning.src.viewmodel

import android.media.MediaRecorder
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.orhanobut.logger.Logger
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.prography.lemorning.ApplicationClass
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.config.Constants
import org.prography.lemorning.src.apis.CreateSongApi
import org.prography.lemorning.utils.base.BaseEvent
import java.io.File
import java.util.concurrent.TimeUnit

class CreateSongViewModel : BaseViewModel() {

    var profileUri : MutableLiveData<Uri?> = MutableLiveData()
    var canFinish: MutableLiveData<BaseEvent<Boolean>> = MutableLiveData()

    var isRecording: Boolean? = null
    var curAmpitude: MutableLiveData<Int> = MutableLiveData()

    fun postSong(name: String, filePath: String) {
        rxDisposable.add(ApplicationClass.retrofit.create(CreateSongApi::class.java)
            .postSong(
                name = name.toRequestBody(),
                number = 10.toString().toRequestBody(),
                price = 100.toString().toRequestBody(),
                stock = 50.toString().toRequestBody(),
                alarm = MultipartBody.Part.createFormData("alarm", filePath, File(filePath).asRequestBody(Constants.MEDIA_TYPE_FORM_DATA.toMediaType())),
                image = MultipartBody.Part.createFormData("image", File(profileUri.value?.path!!).name,
                    File(profileUri.value?.path!!).asRequestBody(Constants.MEDIA_TYPE_FORM_DATA.toMediaType()))
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                toastEvent.value = BaseEvent("업로드에 성공했습니다.")
                canFinish.value = BaseEvent(true)
            }, {
                it.printStackTrace()
                toastEvent.value = BaseEvent("네트워크 연결이 원활하지 않습니다.")
            }))
    }

    fun registerTimerOnAudio(mediaRecorder: MediaRecorder) {
        Logger.d("Registered")
        rxDisposable.add(
            Observable.timer(100, TimeUnit.MILLISECONDS)
            .repeat()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { isRecording?.let { if (it) curAmpitude.value = mediaRecorder.maxAmplitude }})
    }
}