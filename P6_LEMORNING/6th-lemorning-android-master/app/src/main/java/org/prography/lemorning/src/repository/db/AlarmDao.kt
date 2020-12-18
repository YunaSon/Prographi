package org.prography.lemorning.src.repository.db

import androidx.lifecycle.LiveData
import androidx.room.*
import org.prography.lemorning.src.models.Alarm

@Dao
interface AlarmDao{
    @Query("SELECT * FROM alarm ORDER BY id")
    fun getAll(): LiveData<List<Alarm>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(alarm: Alarm)

    @Delete
    fun delete(alarm: Alarm)

    @Query("SELECT * FROM alarm WHERE id = :id")
    fun getAlarm(id : Int): Alarm
  
    @Update
    fun update(alarm: Alarm)
}