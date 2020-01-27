package com.example.a22housexam2.Presenter

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import com.example.a22housexam2.Activities.ViewInterface
import com.example.a22housexam2.App

class InternetCheckPresenter : PresenterInterface.LoadPresenter{

    lateinit var view : ViewInterface.LoadView

    override fun getInternetState() {
        var context : Context = App.applicationContext()
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
        if(isConnected){
            this.view.setInternetState(true)
        }
        else{
            this.view.setInternetState(false)
        }
    }

    constructor(view : ViewInterface.LoadView){
        this.view = view
    }

}