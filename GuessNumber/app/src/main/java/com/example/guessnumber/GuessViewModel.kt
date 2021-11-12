package com.example.guessnumber

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class GuessViewModel: ViewModel() {
    private var secret: Int;
    private var count: Int;
    val counter = MutableLiveData<Int>()
    val result = MutableLiveData<GameResult>()
    init{
        count = 0
        secret = Random().nextInt(10) + 1
    }

    fun  guess(num: Int){
        count++
        val gameResult =
            when(num - secret){
                0 -> GameResult.NumberRight
                in 1..Int.MAX_VALUE -> GameResult.Smaller
                else -> GameResult.Bigger
            }
        counter.value = count
        result.value = gameResult
    }
    fun reset(){
        secret = Random().nextInt(10) + 1
        count = 0
        counter.value = count
    }
}
enum class GameResult{
    Bigger,
    Smaller,
    NumberRight
}