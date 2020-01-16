package com.example.a22housexam2.Activities

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.a22housexam2.Networking.Service.PcRequestManager

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        startLoading()
    }
    fun startLoading(){
        var request = PcRequestManager()
        request.requestPc()
        var handler = Handler()
        handler.postDelayed(Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }
}