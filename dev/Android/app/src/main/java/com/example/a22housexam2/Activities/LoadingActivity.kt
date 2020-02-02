package com.example.a22housexam2.Activities

import android.app.Activity
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.a22housexam2.App
import com.example.a22housexam2.Networking.Service.PcRequestManager
import com.example.a22housexam2.Presenter.InternetCheckPresenter
import com.example.a22housexam2.Utility.Utils

class LoadingActivity : AppCompatActivity(), ViewInterface.LoadView {
    lateinit var internetPresenter : InternetCheckPresenter
    var context = App.applicationContext()

    override fun setInternetState(flag : Boolean){
        if(flag){
            startLoading()
        }else{
            // 종료
            var dialog = AlertDialog.Builder(this)
            dialog.setTitle("NETWORK ERROR")
            dialog.setMessage("Check your internet status")

            var dialog_listener = object: DialogInterface.OnClickListener{
                @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    when(which){
                        DialogInterface.BUTTON_POSITIVE ->
                            finishAffinity()
                    }
                }
            }

            dialog.setPositiveButton("Ok",dialog_listener)
            dialog.show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        Utils.setStatusBarColor(this, Utils.StatusBarColorType.WHITE_STATUS_BAR);
        this.internetPresenter = InternetCheckPresenter(this)
        checkInternet()
    }

    fun checkInternet(){
        this.internetPresenter.getInternetState()
    }

    fun startLoading(){
        PcRequestManager.requestPc()
        var handler = Handler()
        handler.postDelayed(Runnable {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }
}