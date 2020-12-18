package org.prography.lemorning.src.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.ApplicationClass
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.adapters.CategoryAdapter
import org.prography.lemorning.src.apis.SearchApiService
import org.prography.lemorning.src.models.Category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(var keyword: String? = null) : BaseViewModel() {

    val categoryAdapter = CategoryAdapter(viewmodel = this)
    val categoryList : LiveData<ArrayList<Category?>> = getCategories()

    fun getCategories() : LiveData<ArrayList<Category?>> {
        val result: MutableLiveData<ArrayList<Category?>> = MutableLiveData()
        ApplicationClass.retrofit.create(SearchApiService::class.java).getCategory().enqueue(object :
            Callback<ArrayList<Category?>> {
            override fun onFailure(call: Call<ArrayList<Category?>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ArrayList<Category?>>,
                response: Response<ArrayList<Category?>>
            ) {
                result.value = response.body()
            }
        })
        return result
    }
}