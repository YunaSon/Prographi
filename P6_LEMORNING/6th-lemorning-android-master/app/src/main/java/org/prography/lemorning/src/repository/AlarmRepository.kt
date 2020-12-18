package org.prography.lemorning.src.repository

import android.app.Application
import androidx.lifecycle.LiveData
import org.prography.lemorning.src.models.Alarm
import org.prography.lemorning.src.repository.db.AlarmDao
import org.prography.lemorning.src.repository.db.AlarmDatabase

class AlarmRepository(application: Application){
    private val alarmDatabase = AlarmDatabase.getInstance(
        application
    )!!
    private val alarmDao: AlarmDao = alarmDatabase.alarmDao()
    private val alarms: LiveData<List<Alarm>> = alarmDao.getAll()

    fun getAll(): LiveData<List<Alarm>> {
        return alarms
    }

    fun insert(alarm: Alarm){
        try{
            val thread = Thread(Runnable {
                alarmDao.insert(alarm)
            })
            thread.start()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun delete(alarm: Alarm){
        try{
            val thread = Thread(Runnable {
                alarmDao.delete(alarm)
            })
            thread.start()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    fun getAlarm(id: Int): Alarm? {
        var alarm: Alarm? = null
        try{
            val thread = Thread(Runnable {
                alarm = alarmDao.getAlarm(id)
            })
            thread.start()
        } catch (e: Exception){
            e.printStackTrace()
        }
        return alarm
    }

    fun update(alarm: Alarm){
        try{
            val thread = Thread(Runnable {
                alarmDao.update(alarm)
            })
            thread.start()
        } catch (e: Exception){
            e.printStackTrace()
        }
    }
}