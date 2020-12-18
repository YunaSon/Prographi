package org.prography.lemorning.src

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import org.prography.lemorning.src.models.Alarm

class AlarmContextContent(private val context: Context){

    fun getPendingIntent(alarm: Alarm): PendingIntent?{
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("id", alarm.id)
            putExtra("songNo", alarm.songNo)
            putExtra("week", alarm.week)
            putExtra("date", alarm.date)
            putExtra("alarmNote", alarm.alarmNote)
        }

        return alarm.id?.let {
            PendingIntent.getBroadcast(
                context,
                it,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }

    fun getAlarmManager():AlarmManager{
        return context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    }

    fun getPackageManager():PackageManager{
        return context.packageManager
    }

    fun getComponentName():ComponentName{
        return ComponentName(context, DeviceBootReceiver::class.java)
    }
}