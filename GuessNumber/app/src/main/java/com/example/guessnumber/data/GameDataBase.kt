package com.example.guessnumber.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = arrayOf(Record::class),version = 1)
abstract class GameDataBase: RoomDatabase(){

    abstract fun recordDao(): RecordDao
    companion object {
        // 在抽象類別中建置統一管理，不然就會在每個頁面都建立會有衝突的風險
        private val dbName: String = "game.db"
        // 這個db可能還沒建立預設值給null
        private var dataBase: GameDataBase? = null
        fun getGameDataBase(context: Context): GameDataBase?
        {
            // 如果不存在就建立
            if(dataBase == null)
            {
                dataBase = Room.databaseBuilder(context,GameDataBase::class.java, dbName).build();
            }
            return dataBase
        }
    }
}