package org.prography.lemorning.utils.components

import androidx.lifecycle.MutableLiveData
import com.google.firebase.crashlytics.FirebaseCrashlytics

class NetworkEvent : MutableLiveData<NetworkEvent.NetworkState>() {


    enum class NetworkState {
        LOADING, FAILURE, SUCCESS, ERROR
    }

    fun startLoading() {
        value = NetworkState.LOADING
    }

    fun handleResponse(response : Any?) {
        if (response is Throwable?) {
            FirebaseCrashlytics.getInstance().log(response?.message ?: "Empty message")
            value = when (response?.message) {
                NetworkState.FAILURE.toString() -> NetworkState.FAILURE
                else -> NetworkState.ERROR
            }
        } else {
            value = NetworkState.SUCCESS
        }
    }
}