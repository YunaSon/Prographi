package org.prography.lemorning.src.apis

import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CreateSongApi {

    @Multipart
    @POST("/shop/product")
    fun postSong(@Part("name") name: RequestBody?,
                 @Part("number") number: RequestBody?,
                 @Part("price") price: RequestBody?,
                 @Part("stock") stock: RequestBody?,
                 @Part image: MultipartBody.Part?,
                 @Part alarm: MultipartBody.Part?) : Single<Any>
}