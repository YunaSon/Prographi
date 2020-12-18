package org.prography.lemorning

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import com.google.firebase.FirebaseApp
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import com.orhanobut.logger.PrettyFormatStrategy
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.prography.lemorning.config.XAccessTokenInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class ApplicationClass : Application() {

    // 테스트 서버 주소
    companion object {

        var BASE_URL: String = ""
        // 실서버 주소
        //    public static String BASE_URL = "https://template.prography.org/";

        lateinit var sSharedPreferences: SharedPreferences

        // SharedPreferences 키 값
        val TAG = "LE-MORNING"

        // JWT Token 값
        val X_ACCESS_TOKEN = "X-ACCESS-TOKEN"


        // LANGUAGE 설정 값
        val LANGUAGE = "LANGUAGE"

        // Retrofit 인스턴스
        val httpLoggingInterceptor = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                if (message.startsWith("{") && message.endsWith("}")) {
                    Logger.t("OKHTTP").json(message)
                } else {
                    Log.i("OKHTTP", message)
                }
            }
        }).apply { setLevel(HttpLoggingInterceptor.Level.BODY) }
        val client: OkHttpClient = OkHttpClient.Builder()
            .readTimeout(6000, TimeUnit.MILLISECONDS)
            .connectTimeout(6000, TimeUnit.MILLISECONDS)
            .addNetworkInterceptor(XAccessTokenInterceptor()) // JWT 자동 헤더 전송
            .addNetworkInterceptor(httpLoggingInterceptor)
            .build()

        lateinit var retrofit: Retrofit
    }

    override fun onCreate() {
        super.onCreate()
        sSharedPreferences = applicationContext.getSharedPreferences(TAG, Context.MODE_PRIVATE)

        Logger.addLogAdapter(object : AndroidLogAdapter(PrettyFormatStrategy.newBuilder()
            .showThreadInfo(false)
            .methodCount(0)
            .build()) {
            override fun isLoggable(priority: Int, tag: String?): Boolean = BuildConfig.DEBUG
        })

        FirebaseApp.initializeApp(this)
    }
}