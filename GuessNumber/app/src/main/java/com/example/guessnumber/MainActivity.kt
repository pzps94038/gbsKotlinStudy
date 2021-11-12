package com.example.guessnumber

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.KeyEvent
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import com.example.guessnumber.data.GameDataBase
import com.example.guessnumber.data.Record
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    // 先宣告等等會有一個 viewModel的物件
    // 在onCreate實體化,因為實體化只能在onCreate之後
    private lateinit var viewModel: GuessViewModel;
    private val guessRequestCode = 200;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 設定viewModel提供者
        viewModel = ViewModelProvider(this).get(GuessViewModel::class.java)
        // 監聽counter如果有變動會重新調整文字
        viewModel.counter.observe(this, Observer { a ->
            count.setText(a.toString ())
        })
        // region 監聽Result
        viewModel.result.observe(this,{ a ->
            var message: String = ""
            when(a){
                GameResult.Bigger -> message = getString(R.string.bigger)
                GameResult.Smaller -> message = getString(R.string.smaller)
                GameResult.NumberRight -> message = getString(R.string.yes_you_got_it)
            }
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.result))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    if (message.equals(getString(R.string.yes_you_got_it))) {
                        //val intent = Intent(this, RecordActivity::class.java)
                        //intent.putExtra("Counter", viewModel.counter.value)
                        //startActivity(intent);
                        //startActivityForResult(intent,guessRequestCode)
                        Intent(this,RecordActivity::class.java).apply {
                            putExtra("Counter", viewModel.counter.value)
                        }.also {
                            startActivityForResult(it,guessRequestCode)
                        }
                    }

                }
                .show()
        })
        // endregion
        //使用Room
        // val database = Room.databaseBuilder(this,GameDataBase::class.java,"game.db").build()
        var database = GameDataBase.getGameDataBase(this);
        val record = Record("Jack",3)

        Thread {
            database?.recordDao()?.inSert(record)
        }.start()

        initView();
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode.equals(guessRequestCode)){
            if(resultCode.equals(RESULT_OK)){
                val name = data?.getStringExtra("Nick");
                if (name != null) {
                    Log.d("Nick",name)
                }
                rePlay()
            }
        }
    }
    private fun initView(){
        // 重置按鈕
        reset_btn.setOnClickListener {
            rePlay()
        }
        // 確認按鈕
        check_btn.setOnClickListener {
            if(ed_number.text.toString() != ""){
                viewModel.guess(ed_number.text.toString().toInt())
            }
        }
        ed_number.setOnKeyListener(View.OnKeyListener { v, keyCode, event ->
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.action == KeyEvent.ACTION_UP) {
                if(ed_number.text.toString() != ""){
                    viewModel.guess(ed_number.text.toString().toInt())
                }
                return@OnKeyListener true
            }
            false
        })
    }
    private fun rePlay(){
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.dialog_title))
            .setMessage(getString(R.string.are_you_sure_you_want_to_reset))
            .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                // SecretNumber.reset()
                viewModel.reset()
            }
            .setNegativeButton(getString(R.string.cancel),null)
            .show()
        ed_number.setText("")
    }

}