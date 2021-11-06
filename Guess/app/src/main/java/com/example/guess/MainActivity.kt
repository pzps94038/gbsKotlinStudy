    package com.example.guess

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*

    class MainActivity : AppCompatActivity() {
    val SecretNumber = SecretNumber();
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MainActivity","number:" + SecretNumber.secret.toString())
    }
    fun check(view: View){
        var number: Int = ed_number.text.toString().toInt();
        var message: String = "";
        if(SecretNumber.secret > number){
            message = "大一點"
        }
        else if(SecretNumber.secret.equals(number)){
            message = "答對了"
        }
        else{
            message = "小一點"
        }
        // Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        AlertDialog.Builder(this)
            .setTitle("Message")
            .setMessage(message)
            .setPositiveButton("OK", null)
            .show()
    }
}