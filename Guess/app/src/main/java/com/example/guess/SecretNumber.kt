package com.example.guess

import java.util.*

class SecretNumber {
    val secret: Int = Random().nextInt(10) + 1;
    var count: Int = 0;
    fun vaildate(number: Int): Int{
        count++;
        return number - secret
    };
}
fun main(){
    val SecretNumber = SecretNumber();
    println(SecretNumber.secret)
    println("${SecretNumber.vaildate(2)},count: ${SecretNumber.count}")
}