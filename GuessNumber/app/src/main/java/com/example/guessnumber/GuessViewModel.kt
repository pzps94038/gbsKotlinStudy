package com.example.guessnumber

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class GuessViewModel: ViewModel() {
    var secret: Int = 0;
    private var count: Int = 0;
    val counter = MutableLiveData<Int>()
    val result = MutableLiveData<GameResult>()
    init{
        count = 0
        secret = Random().nextInt(10) + 1;
    }

    fun  guess(num: Int){
        count++;
        var gameresult =
            when(num - secret){
                0 -> GameResult.Number_Right
                in 1..Int.MAX_VALUE -> GameResult.Smaller
                else -> GameResult.Bigger
            }
        counter.value = count;
        result.value = gameresult;
    }
    fun reset(){
        secret = Random().nextInt(10) + 1;
        counter.value = 0;
    }
}
enum class GameResult{
    Bigger,
    Smaller,
    Number_Right
}