package org.prography.lemorning.src.apis

import org.prography.lemorning.src.models.PlaySong
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface PlayAlarmApiService {
    @GET("/shop/lists/{songNo}")
    fun getPlaySong(@Path("songNo") songNo: Int) : Call<PlaySong>

    @GET("/shop/lists/")
    fun getSongs() : Call<ArrayList<PlaySong?>>
}