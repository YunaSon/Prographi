package org.prography.lemorning.src.viewmodel

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.ApplicationClass
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.adapters.ForYouAdapter
import org.prography.lemorning.src.adapters.PopularAdapter
import org.prography.lemorning.src.apis.TrendingApiService
import org.prography.lemorning.src.models.ForYou
import org.prography.lemorning.src.models.Popular
import org.prography.lemorning.utils.base.BaseEvent
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.util.Pair as UtilPair

class TrendingViewModel : BaseViewModel() {

    var forYouAdapter = ForYouAdapter(viewModel = this)
    var popularAdapter = PopularAdapter(viewModel = this)

    var forYouList : LiveData<ArrayList<ForYou?>> = getForYous()
    var popularList: LiveData<ArrayList<Popular?>> = getPopulars()

    var moveToSong: MutableLiveData<BaseEvent<Int>> = MutableLiveData()

    fun getForYous() : LiveData<ArrayList<ForYou?>> {
        var result = MutableLiveData<ArrayList<ForYou?>>()
        ApplicationClass.retrofit.create(TrendingApiService::class.java).getForYous().enqueue(object : Callback<ArrayList<ForYou?>> {

            override fun onFailure(call: Call<ArrayList<ForYou?>>, t: Throwable) {
                t.printStackTrace()
                alertEvent.value = BaseEvent("네트워크 연결을 확인해주세요.")
            }

            override fun onResponse(
                call: Call<ArrayList<ForYou?>>,
                response: Response<ArrayList<ForYou?>>
            ) {
                response.body()?.let { result.value = it }
            }
        })
        return result
    }

    fun getPopulars() : LiveData<ArrayList<Popular?>> {
        var result = MutableLiveData<ArrayList<Popular?>>()
        ApplicationClass.retrofit.create(TrendingApiService::class.java).getPopulars().enqueue(object : Callback<ArrayList<Popular?>> {

            override fun onFailure(call: Call<ArrayList<Popular?>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(
                call: Call<ArrayList<Popular?>>,
                response: Response<ArrayList<Popular?>>
            ) {
                var baseResponse = response.body();
                if (!response.isSuccessful || baseResponse == null) {
                    return
                }
                result.value = baseResponse
            }
        })
        return result
    }

    fun onClickSong(songNo: Int, vararg pairs: UtilPair<View, String>?) {
        moveToSong.value = BaseEvent(songNo)
    }
}