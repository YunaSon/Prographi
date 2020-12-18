package org.prography.lemorning.src.repository.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import org.prography.lemorning.src.models.Alarm

@Database(entities = [Alarm::class], version = 1, exportSchema = false)
abstract class AlarmDatabase: RoomDatabase() {

    abstract fun alarmDao(): AlarmDao

    companion object{
        private var INSTANCE: AlarmDatabase? = null

        fun getInstance(context: Context): AlarmDatabase?{
            if(INSTANCE == null){
                synchronized(AlarmDatabase::class){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        AlarmDatabase::class.java, "alarm")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE
        }
    }
}