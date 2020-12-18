package org.prography.lemorning.utils

import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import org.prography.lemorning.BuildConfig
import org.prography.lemorning.R

object FirebaseUtils {
    const val BASE_URL_KEY = "server_host_url"

    @JvmStatic
    fun initRemoteConfig(fetchAction: (FirebaseRemoteConfig) -> Unit, activeAction: ((FirebaseRemoteConfig) -> Unit)?) {
        var remoteConfig = FirebaseRemoteConfig.getInstance().apply {
            setConfigSettingsAsync(
                FirebaseRemoteConfigSettings.Builder()
                    .setMinimumFetchIntervalInSeconds(if (BuildConfig.DEBUG) 600 else 3600)
                    .build()
            )
            setDefaultsAsync(R.xml.firebase_remote_config)
        }
        fetchAction.invoke(remoteConfig)
        remoteConfig.fetchAndActivate().addOnCompleteListener { if (it.isSuccessful) { activeAction?.invoke(remoteConfig) ?: fetchAction.invoke(remoteConfig) } }
    }
}