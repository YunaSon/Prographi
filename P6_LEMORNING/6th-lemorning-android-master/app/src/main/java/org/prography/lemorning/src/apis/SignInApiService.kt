package org.prography.lemorning.src.apis

import io.reactivex.Single
import org.prography.lemorning.src.models.Jwt
import org.prography.lemorning.src.models.SignInParam
import retrofit2.http.Body
import retrofit2.http.POST

interface SignInApiService {
    @POST("/login")
    fun postToken(@Body param : SignInParam) : Single<Jwt>
}