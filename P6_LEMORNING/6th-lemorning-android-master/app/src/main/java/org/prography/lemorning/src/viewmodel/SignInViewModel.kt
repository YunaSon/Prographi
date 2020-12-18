package org.prography.lemorning.src.viewmodel

import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.prography.lemorning.ApplicationClass
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.apis.SignInApiService
import org.prography.lemorning.src.models.Jwt
import org.prography.lemorning.src.models.SignInParam
import org.prography.lemorning.utils.Validators
import org.prography.lemorning.utils.base.BaseEvent

class SignInViewModel : BaseViewModel() {

    companion object {
        const val SIGN_IN_SUCCESS = 10
        const val SIGN_IN_FAILURE_NEED_SIGN_UP = 11
    }

    var email : MutableLiveData<String> = MutableLiveData("")
    var password : MutableLiveData<String> = MutableLiveData("")

    var navTo : MutableLiveData<BaseEvent<Int>> = MutableLiveData()

    fun onClickSignIn() {
        if (!Validators.isValidEmail(email.value) || password.value?.length == 0) {
            toastEvent.value = BaseEvent("이메일 형식을 지켜주세요.")
            return
        }
        rxDisposable.add(trySignIn(email.value, password.value).subscribe({
            it.token?.let {
                ApplicationClass.sSharedPreferences.edit().putString(ApplicationClass.X_ACCESS_TOKEN, it).apply()
                toastEvent.value = BaseEvent("환영합니다.")
                navTo.value = BaseEvent(SIGN_IN_SUCCESS)
            }
        }, {
            it.printStackTrace()
            toastEvent.value = BaseEvent("로그인에 실패하였습니다.")
        }))
    }

    fun trySignIn(email : String?, password : String?) : Single<Jwt> {
        return ApplicationClass.retrofit.create(SignInApiService::class.java)
            .postToken(SignInParam(email, password))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}