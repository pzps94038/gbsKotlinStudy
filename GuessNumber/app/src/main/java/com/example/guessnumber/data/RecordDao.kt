package com.example.guessnumber.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RecordDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun inSert(record: Record)

//    @Query("select nick from record")
//    fun getAll():List<Record>

}