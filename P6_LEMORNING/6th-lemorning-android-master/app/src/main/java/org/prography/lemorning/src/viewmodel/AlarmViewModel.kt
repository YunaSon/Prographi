package org.prography.lemorning.src.viewmodel

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.pm.PackageManager
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.TimePicker
import androidx.core.view.get
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import org.prography.lemorning.ApplicationClass
import org.prography.lemorning.BaseViewModel
import org.prography.lemorning.src.apis.PlayAlarmApiService
import org.prography.lemorning.src.models.Alarm
import org.prography.lemorning.src.models.PlaySong
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class AlarmViewModel: BaseViewModel() {

    var songs: LiveData<ArrayList<PlaySong?>> = getAlarmSongs()
    var alarmNote = ""

    fun setAlarm(timePicker: TimePicker, linearLayout: LinearLayout, songNo: Int, imgUrl: String): Alarm {
        val hour = timePicker.hour
        val minute = timePicker.minute

        var week = ""

        for (i in 0..6) {
            if ((linearLayout[i] as CheckBox).isChecked) {
                week += "1"
            } else week += "0"
        }

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()
        calendar.set(Calendar.HOUR_OF_DAY, hour)
        calendar.set(Calendar.MINUTE, minute)
        calendar.set(Calendar.SECOND, 0)

        if (calendar.before(Calendar.getInstance())) calendar.add(Calendar.DATE, 1)

        val currentTime = calendar.time
        val timeText = SimpleDateFormat(
            "a hh : mm",
            Locale.getDefault()
        ).format(currentTime)

        return Alarm(null, timeText, true, week, currentTime.time, songNo, imgUrl, alarmNote)

    }

    fun setAlarmManager(alarm: Alarm, pendingIntent: PendingIntent, alarmManager: AlarmManager) {
        val calendar = Calendar.getInstance()

        val date = Date(alarm.date)
        calendar.time = date

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    fun setBootAlarm(packageManager: PackageManager, receiver: ComponentName) {
        packageManager.setComponentEnabledSetting(
            receiver,
            PackageManager.COMPONENT_ENABLED_STATE_ENABLED,
            PackageManager.DONT_KILL_APP
        )
    }

    private fun getAlarmSongs(): LiveData<ArrayList<PlaySong?>> {
        val songs: MutableLiveData<ArrayList<PlaySong?>> = MutableLiveData()

        ApplicationClass.retrofit.create(PlayAlarmApiService::class.java).getSongs().enqueue(object :
            Callback<ArrayList<PlaySong?>> {
            override fun onFailure(call: Call<ArrayList<PlaySong?>>, t: Throwable) {
                t.printStackTrace()
            }

            override fun onResponse(call: Call<ArrayList<PlaySong?>>, response: Response<ArrayList<PlaySong?>>) {
                response.body()?.let { songs.value = it }
            }
        })

        return songs
    }


}