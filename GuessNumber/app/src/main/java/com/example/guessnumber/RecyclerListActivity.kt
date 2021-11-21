package com.example.guessnumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.guessnumber.data.GameDataBase
import kotlinx.android.synthetic.main.activity_recycler_list.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class RecyclerListActivity : AppCompatActivity() , CoroutineScope{
    private lateinit var job: Job
    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
        setContentView(R.layout.activity_recycler_list)
        bindView()
    }
    private fun bindView(){
        launch {
            val db = GameDataBase.getGameDataBase(this@RecyclerListActivity)
            val listRecord = db?.recordDao()?.getAll()
            // 資料取出後渲染畫面
            listRecord?.apply {
                recyclerList.layoutManager = LinearLayoutManager(this@RecyclerListActivity)
                recyclerList.setHasFixedSize(true)
                recyclerList.adapter = RecyclerAdapter(this)
            }
        }
//        CoroutineScope(Dispatchers.IO).launch {
//            val db = GameDataBase.getGameDataBase(this@RecyclerListActivity)
//            val listRecord = db?.recordDao()?.getAll()
//            // 資料取出後渲染畫面
//            listRecord?.apply {
//                runOnUiThread {
//                    recyclerList.layoutManager = LinearLayoutManager(this@RecyclerListActivity)
//                    recyclerList.setHasFixedSize(true)
//                    recyclerList.adapter = RecyclerAdapter(this)
//                }
//            }
//        }
//        Thread()
//        {
//            val db = GameDataBase.getGameDataBase(this)
//            var listRecord = db?.recordDao()?.getAll();
//            listRecord?.let {
//                runOnUiThread{
//                    recyclerList.layoutManager = LinearLayoutManager(this)
//                    recyclerList.setHasFixedSize(true)
//                    recyclerList.adapter = RecyclerAdapter(it)
//
//                }
//            }
//        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }
}