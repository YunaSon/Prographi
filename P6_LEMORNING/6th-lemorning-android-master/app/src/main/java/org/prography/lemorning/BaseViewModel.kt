package org.prography.lemorning

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import org.prography.lemorning.utils.base.BaseEvent
import org.prography.lemorning.utils.components.NetworkEvent

abstract class BaseViewModel : ViewModel() {

    protected val rxDisposable = CompositeDisposable()

    var networkEvent = NetworkEvent()
    var toastEvent : MutableLiveData<BaseEvent<String>> = MutableLiveData()
    var alertEvent : MutableLiveData<BaseEvent<String>> = MutableLiveData()

    override fun onCleared() {
        super.onCleared()
        if (!rxDisposable.isDisposed) rxDisposable.dispose()
    }
}