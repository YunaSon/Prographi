package org.prography.lemorning.src

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import java.util.*

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val calendar = Calendar.getInstance()
        val date = intent?.getLongExtra("date", 0)?.let { Date(it) }
        calendar.time = date
        calendar.add(Calendar.DATE, 1)

        intent?.putExtra("date", calendar.timeInMillis)
        val pendingIntent =
            intent?.getIntExtra("id", -1)?.let { PendingIntent.getBroadcast(context, it, intent, PendingIntent.FLAG_CANCEL_CURRENT) }

        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )

        intent?.getStringExtra("week").let {
            if(it?.get(Calendar.getInstance().get(Calendar.DAY_OF_WEEK) - 1) == '0'){
                return
            }
            if(it == null) return
        }

        val alarmIntent = Intent(context, AlarmService::class.java).apply {
            putExtra("songNo", intent?.getIntExtra("songNo", -1))
            putExtra("alarmNote", intent?.getStringExtra("alarmNote"))
            action = "AlarmStart"
        }

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            context.startForegroundService(alarmIntent)
        }else{
            context.startService(alarmIntent)
        }
    }


}