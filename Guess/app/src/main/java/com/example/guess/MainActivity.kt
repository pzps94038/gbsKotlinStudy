    package com.example.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_main.*

    class MainActivity : AppCompatActivity() {
        // 先宣告等等會有一個 viewModel的物件
        // 在onCreate實體化,因為實體化只能在onCreate之後
        private lateinit var viewModel: GuessViewModel;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProvider(this).get(com.example.guess.GuessViewModel::class.java)
        //監聽couter如果有變動會重新調整文字
        viewModel.counter.observe(this, Observer { a ->
            count.setText(a.toString())
        })

        viewModel.result.observe(this,{ a ->
            var message: String = ""
            when(a){
                GameResult.Bigger -> message = getString(R.string.bigger)
                GameResult.Smaller -> message = getString(R.string.smaller)
                GameResult.Number_Right -> message = getString(R.string.yes_you_got_it)
            }
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.result))
                .setMessage(message)
                .setPositiveButton(getString(R.string.ok), null)
                .show()
        })
        InitView();
    }
    fun InitView(){
        // 重置按鈕
        reset_btn.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle(getString(R.string.dialog_title))
                .setPositiveButton(getString(R.string.ok)) { dialog, which ->
                    // SecretNumber.reset()
                   viewModel.reset()
                }
                .setNegativeButton(getString(R.string.cancel),null)
                .show()
        }
        // 確認按鈕
        check_btn.setOnClickListener {
            if(ed_number.text.toString() != ""){
                viewModel.guess(ed_number.text.toString().toInt())
            }
        }
    }
}