package com.example.guessnumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guessnumber.data.GameDataBase
import kotlinx.android.synthetic.main.activity_recycler_list.*

class RecyclerListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_list)
        bindView()
    }
    private fun bindView(){
        Thread()
        {
            val db = GameDataBase.getGameDataBase(this)
            var listRecord = db?.recordDao()?.getAll();
            listRecord?.let {
                runOnUiThread{
                    recyclerList.layoutManager = LinearLayoutManager(this)
                    recyclerList.setHasFixedSize(true)
                    recyclerList.adapter = RecyclerAdapter(it)

                }
            }
        }.start()
    }
}