package org.prography.lemorning.src.view


import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_alarm_setting.*
import org.prography.lemorning.BaseActivity
import org.prography.lemorning.R
import org.prography.lemorning.databinding.ActivityAlarmSettingBinding
import org.prography.lemorning.src.AlarmContextContent
import org.prography.lemorning.src.AlarmService
import org.prography.lemorning.src.adapters.AlarmSettingRecyclerAdapter
import org.prography.lemorning.src.viewmodel.AlarmDBViewModel
import org.prography.lemorning.src.viewmodel.AlarmViewModel


class AlarmSettingActivity(override val layoutId: Int = R.layout.activity_alarm_setting) : BaseActivity<ActivityAlarmSettingBinding, AlarmViewModel>() {

    override fun getViewModel(): AlarmViewModel {
        return ViewModelProvider(this)
            .get(AlarmViewModel::class.java)
    }

    override fun initView(savedInstanceState: Bundle?) {
        val dbViewModel = ViewModelProvider(this).get(AlarmDBViewModel::class.java)

        alarm_setting_button.setOnClickListener {
            val contextContent = AlarmContextContent(applicationContext)

            val alarmSongNo = (alarm_setting_recycler.adapter as AlarmSettingRecyclerAdapter).selectItemSongNo
            val alarmImgUrl = (alarm_setting_recycler.adapter as AlarmSettingRecyclerAdapter).selectItemUrl

            if(alarmSongNo == -1){
                showToast("음악을 선택해주세요")
                return@setOnClickListener
            }

            val alarm = viewmodel.setAlarm(alarm_setting_time_picker, alarm_setting_week, alarmSongNo, alarmImgUrl)

            if (alarm.week == "0000000") {
                showToast("요일을 선택해주세요")
            } else {
                dbViewModel.insert(alarm)
                dbViewModel.getAll().observe(this, Observer {
                    for (a in it) {
                        if (alarm.date == a.date) {
                            contextContent.getPendingIntent(a)?.let { pendingIntent ->
                                viewmodel.setAlarmManager(a, pendingIntent, contextContent.getAlarmManager())
                            }
                            viewmodel.setBootAlarm(contextContent.getPackageManager(), contextContent.getComponentName())
                        }
                    }
                })
                showToast(alarm.time + "으로 알람이 설정되었습니다")

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                    startForegroundService(Intent(this, AlarmService::class.java))
                }else{
                    startService(Intent(this, AlarmService::class.java))
                }

                finish()
            }
        }
    }
}