package com.example.guessnumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.row_function.view.*

class RecyclerViewActivity : AppCompatActivity() {
    val functions = listOf<String>(
        "Camera",
        "Guess game",
        "Record list",
        "Download coupons",
        "News",
        "Maps"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        // RecyclerView
        // 調整自己的layoutManager LinearLayoutManger是水平或垂直，另外有grid以及瀑布型
        recycler.layoutManager = LinearLayoutManager(this)
        // 大小是否固定
        recycler.setHasFixedSize(true)
        // 設定Adapter
        recycler.adapter = FunctionAdapter();
    }

    inner class FunctionAdapter(): RecyclerView.Adapter<FunctionHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FunctionHolder {
            // 取得view的方式用底下設計的viewHolder取得
            // 前面參數固定
            // inflate參數放入row的資源layout檔案
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.row_function, parent, false)

            return FunctionHolder(view);
        }

        override fun onBindViewHolder(holder: FunctionHolder, position: Int) {
            // 設置每個holder 可以設置文字、事件等...

            holder.nameText.text = functions[position];
            holder.itemView.setOnClickListener {
                functionClicked(position)
            }
        }

        override fun getItemCount(): Int {
            // 數量
            return functions.size
        }

    }

    private fun functionClicked(position: Int) {
        when(position){
            1 -> startActivity(Intent(this,MainActivity::class.java))
            2 -> startActivity(Intent(this,RecyclerListActivity::class.java))
            else -> return
        }
    }

    class FunctionHolder(view: View): RecyclerView.ViewHolder(view)
    {
        // 綁定每個row
        // view指的是row的layout xml
        // 這裡綁定的是layout裡的textView <- Id
        var nameText: TextView = view.textView
    }
}