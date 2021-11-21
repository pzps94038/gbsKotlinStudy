package com.example.guessnumber

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.guessnumber.data.GameDataBase
import com.example.guessnumber.data.Record
import kotlinx.android.synthetic.main.activity_record.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RecordActivity : AppCompatActivity() , CoroutineScope{
    private lateinit var job : Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
        job = Job()
        val guessCount = intent.getIntExtra("Counter",-1)
        count.setText(guessCount.toString())
        save_btn.setOnClickListener { View ->
            val name = editName.text.toString()
            getSharedPreferences("guess", MODE_PRIVATE)
                .edit()
                .putInt("REC_COUNT", guessCount)
                .putString("Name", name)
                .apply()
            // 存完檔結束回去原本畫面
            // 回傳姓名資料
            val intent = Intent();
            intent.putExtra("Nick", name)
            setResult(Activity.RESULT_OK,intent)
            saveData(name,guessCount)
            finish()
        }
    }
    private fun saveData(name: String,count: Int){
        var data = Record(name,count)
//        CoroutineScope(Dispatchers.IO).launch {
//            val db = GameDataBase.getGameDataBase(this@RecordActivity);
//            db?.recordDao()?.inSert(data)
//        }
        launch {
            val db = GameDataBase.getGameDataBase(this@RecordActivity);
            db?.recordDao()?.inSert(data)
        }
    }


}