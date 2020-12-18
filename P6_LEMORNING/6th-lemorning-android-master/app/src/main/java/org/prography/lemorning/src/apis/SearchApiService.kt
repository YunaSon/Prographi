package org.prography.lemorning.src.apis

import org.prography.lemorning.src.models.Category
import retrofit2.Call
import retrofit2.http.GET

interface SearchApiService {
    @GET("/api/category/")
    fun getCategory() : Call<ArrayList<Category?>>
}