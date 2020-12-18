package org.prography.lemorning.src.apis

import org.prography.lemorning.src.models.ForYou
import org.prography.lemorning.src.models.Popular
import retrofit2.Call
import retrofit2.http.GET

interface TrendingApiService {
    @GET("/shop/lists/")
    fun getForYous() : Call<ArrayList<ForYou?>>

    @GET("/shop/lists/")
    fun getPopulars() : Call<ArrayList<Popular?>>
}