package com.example.guessnumber.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

//Room要存取的資料屬性
@Entity
class Record(@NonNull
             @ColumnInfo(name="nick")
             var nickname: String,
             @NonNull
             var counter: Int
)

{

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0

}