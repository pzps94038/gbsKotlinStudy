package com.example.guessnumber

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_record.*

class RecordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_record)
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
            finish()
        }
    }

    
}