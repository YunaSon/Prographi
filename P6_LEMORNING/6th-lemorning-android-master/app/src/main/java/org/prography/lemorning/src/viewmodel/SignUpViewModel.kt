package org.prography.lemorning.src.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import org.prography.lemorning.ApplicationClass
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.R
import org.prography.lemorning.config.Constants
import org.prography.lemorning.src.apis.SignUpApiService
import org.prography.lemorning.src.models.SignUpResponse
import org.prography.lemorning.utils.Validators
import org.prography.lemorning.utils.base.BaseEvent
import org.prography.lemorning.utils.base.BaseResponse
import org.prography.lemorning.utils.components.NetworkEvent
import java.io.File

class SignUpViewModel : BaseViewModel() {

    var navTo : MutableLiveData<BaseEvent<Int>> = MutableLiveData()

    var email : MutableLiveData<String> = MutableLiveData("")
    var emailStatus : MutableLiveData<Int> = MutableLiveData(Constants.EMAIL_EMPTY)
    var password : MutableLiveData<String> = MutableLiveData("")
    var passwordRe : MutableLiveData<String> = MutableLiveData("")
    var profileUri : MutableLiveData<Uri?> = MutableLiveData()
    var gender : MutableLiveData<String> = MutableLiveData(Constants.MALE)
    var birth : MutableLiveData<String> = MutableLiveData()
    var nickname : MutableLiveData<String> = MutableLiveData("")

    val agreeAll: MutableLiveData<Boolean> = MutableLiveData(false)
    val agreeFirst: MutableLiveData<Boolean> = MutableLiveData(false)
    val agreeSecond: MutableLiveData<Boolean> = MutableLiveData(false)
    val agreeThird: MutableLiveData<Boolean> = MutableLiveData(false)

    fun onClickValidateEmail() {
        emailStatus.value = if (Validators.isValidEmail(email.value)) Constants.EMAIL_VALID
        else Constants.EMAIL_INVALID
    }

    fun onClickGender(idx : Int) {
        gender.value = when(idx) {
            0 -> Constants.MALE
            else -> Constants.FEMALE
        }
    }

    fun onClickAgreement(idx: Int, old: Boolean) {
        when (idx) {
            0 -> {
                agreeAll.value = !old
                agreeFirst.value = !old
                agreeSecond.value = !old
                agreeThird.value = !old
            }
            1 -> {
                agreeFirst.value = !old
                agreeAll.value = agreeFirst.value!! && agreeSecond.value!!
            }
            2 -> {
                agreeSecond.value = !old
                agreeAll.value = agreeFirst.value!! && agreeSecond.value!!
            }
            3 -> {
                agreeThird.value = !old
            }
        }
    }

    fun onClickNext(position : Int) {
        when(position) {
            0 -> {
                if (emailStatus.value != Constants.EMAIL_VALID) {
                    toastEvent.value = BaseEvent("이메일을 확인해주세요.")
                    return
                }
                if (Validators.isValidPasswords(password.value!!, passwordRe.value!!) != Constants.PASSWORD_SAME) {
                    toastEvent.value = BaseEvent("비밀번호를 확인해주세요.")
                    return
                }
                navTo.value = BaseEvent(R.id.action_to_step2)
            }
            1 -> {
                if (profileUri.value == null) {
                    toastEvent.value = BaseEvent("프로필 사진을 선택해주세요.")
                    return
                }
                if (nickname.value!!.isEmpty()) {
                    toastEvent.value = BaseEvent("닉네임을 확인해주세요.")
                    return
                }
                if (birth.value!!.isEmpty() && Validators.isValidBirth(birth.value!!)) {
                    toastEvent.value = BaseEvent("생년월일을 선택해주세요.")
                    return
                }
                navTo.value = BaseEvent(R.id.action_to_step3)
            }
            2 -> {
                if (!agreeAll.value!!) {
                    toastEvent.value = BaseEvent("필수 약관에 모두 동의해주세요.")
                    return
                }
                networkEvent.startLoading()
                rxDisposable.add(tryRegisterUser().subscribe({
                    if (it == 200) {
                        networkEvent.handleResponse(it)
                        navTo.value = BaseEvent(R.id.action_to_step4)
                    }
                    else {
                        networkEvent.handleResponse(Throwable(NetworkEvent.NetworkState.FAILURE.toString()))
                        toastEvent.value = BaseEvent("회원가입에 실패하였습니다.")
                    }
                }, {
                    networkEvent.handleResponse(it)
                    it.printStackTrace()
                }))
            }
            3 -> navTo.value = BaseEvent(-1)
        }
    }

    private fun tryRegisterUser() : Single<Int> {
        val file = File(profileUri.value?.path!!)
        return ApplicationClass.retrofit.create(SignUpApiService::class.java)
            .postUser(email = email.value?.toRequestBody(),
                password = password.value?.toRequestBody(),
                profile = MultipartBody.Part.createFormData("profile", file.name ,file.asRequestBody(Constants.MEDIA_TYPE_FORM_DATA.toMediaType())),
                nickname = nickname.value?.toRequestBody(),
                gender = gender.value?.toRequestBody(),
                birth = birth.value?.toRequestBody()
            )
            .map { it.code }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }

}