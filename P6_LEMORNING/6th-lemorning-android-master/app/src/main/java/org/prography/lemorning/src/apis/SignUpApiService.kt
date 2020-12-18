package org.prography.lemorning.src.apis

import io.reactivex.Single
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.prography.lemorning.src.models.SignUpResponse
import org.prography.lemorning.utils.base.BaseResponse
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface SignUpApiService {

    @Multipart
    @POST("/signup")
    fun postUser(@Part("email") email: RequestBody?,
                 @Part("password") password: RequestBody?,
                 @Part profile: MultipartBody.Part?,
                 @Part("account.nickname") nickname: RequestBody?,
                 @Part("account.sex") gender: RequestBody?,
                 @Part("account.birth") birth: RequestBody?
    ) : Single<BaseResponse<Any>>
}