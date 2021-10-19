package com.developerkurt.screenlogapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.developerkurt.screenlog.ScreenLog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    var screenLog :ScreenLog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        screenLog = ScreenLog(this,this, listOf("com.developerkurt.screenlogapp", "Hello World"))
        MainScope().launch {
            repeat(10){
                Log.d("MainActivity", "Hello World ${it+1}!")
                delay(1000);
            }
        }
    }





    override fun onDestroy() {
        super.onDestroy()
        screenLog?.onDestroyed()
    }
}