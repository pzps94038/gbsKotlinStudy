package com.example.guessnumber

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.*
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_recycler_view.*
import kotlinx.android.synthetic.main.row_function.view.*
import java.util.jar.Manifest

class RecyclerViewActivity : AppCompatActivity() {
    val REQUEST_CODE_CAMERA: Int = 100
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
        // spinner
        val spinnerData = mutableListOf<String>("Blue","Yellow","Red")
        val adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,spinnerData)
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line)
        spinner.adapter = adapter
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Log.d("spinner",spinnerData[position])
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
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
            0 -> {
                val permission = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
                if(permission.equals(PackageManager.PERMISSION_GRANTED)){
                    takePhoto()
                }else{
                    ActivityCompat.requestPermissions(this, arrayOf<String>(android.Manifest.permission.CAMERA),REQUEST_CODE_CAMERA)
                }

            }
            1 -> startActivity(Intent(this,MainActivity::class.java))
            2 -> startActivity(Intent(this,RecyclerListActivity::class.java))
            else -> return
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode == REQUEST_CODE_CAMERA){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                takePhoto()
            }
        }
    }

    private fun takePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivity(intent)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.downLoad){
            Log.d("menuItem","${item.itemId}")
        }
        return super.onOptionsItemSelected(item)
    }

    class FunctionHolder(view: View): RecyclerView.ViewHolder(view)
    {
        // 綁定每個row
        // view指的是row的layout xml
        // 這裡綁定的是layout裡的textView <- Id
        var nameText: TextView = view.textView
    }
}