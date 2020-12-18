package org.prography.lemorning.src.viewmodel

import android.media.MediaPlayer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.ApplicationClass
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.apis.PlaySongApiService
import org.prography.lemorning.src.models.PlaySong
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PlayAlarmViewModel(var songNo: Int) : BaseViewModel() {

    var playSong: LiveData<PlaySong> = getSong(songNo)

    var mediaPlayer: MutableLiveData<MediaPlayer?> = MutableLiveData()

    private fun getSong(songNo: Int) : LiveData<PlaySong> {
        val result: MutableLiveData<PlaySong> = MutableLiveData()
        ApplicationClass.retrofit.create(PlaySongApiService::class.java).getPlaySong(songNo).enqueue(object : Callback<PlaySong> {
            override fun onFailure(call: Call<PlaySong>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<PlaySong>, response: Response<PlaySong>) {
                val playSong : PlaySong = response.body() ?: return
                result.value = playSong
                mediaPlayer.value = MediaPlayer().apply {
                    setDataSource(playSong.musicUrl)
                    setScreenOnWhilePlaying(true)
                    isLooping = true
                    prepareAsync() // might take long! (for buffering, etc)
                }
            }

        })
        return result
    }

    override fun onCleared() {
        super.onCleared()
        mediaPlayer.value?.release()
    }
}