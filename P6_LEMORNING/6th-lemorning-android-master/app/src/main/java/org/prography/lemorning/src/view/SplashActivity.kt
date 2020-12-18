package org.prography.lemorning.src.view

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import org.prography.lemorning.ApplicationClass
import org.prography.lemorning.ApplicationClass.Companion.BASE_URL
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivitySplashBinding
import org.prography.lemorning.src.view.signin.SignInActivity
import org.prography.lemorning.src.viewmodel.SplashViewModel
import org.prography.lemorning.utils.FirebaseUtils
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class SplashActivity(override val layoutId: Int = R.layout.activity_splash)
    : BaseActivity<ActivitySplashBinding, SplashViewModel>() {
    override fun getViewModel(): SplashViewModel = ViewModelProvider(this).get(SplashViewModel::class.java)

    override fun initView(savedInstanceState: Bundle?) {
        FirebaseUtils.initRemoteConfig({
            BASE_URL = it.getString(FirebaseUtils.BASE_URL_KEY)
        }, {
            BASE_URL = it.getString(FirebaseUtils.BASE_URL_KEY)
            ApplicationClass.retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(ApplicationClass.client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            startActivity(Intent(this, SignInActivity::class.java))
            /*startActivity(
                Intent(this, SignInActivity::class.java),
                ActivityOptions.makeSceneTransitionAnimation(this, binding.ivLogoSplash, "logo_lemorning").toBundle()
            )*/
            //finishAfterTransition()
            finish()
            overridePendingTransition(R.anim.anim_fade_in, R.anim.anim_fade_out)
        })
    }

}