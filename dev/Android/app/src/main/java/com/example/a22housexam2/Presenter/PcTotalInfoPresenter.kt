package com.example.a22housexam2.Presenter

import android.util.Log
import android.widget.Toast
import com.example.a22housexam2.Networking.Model.GitHubResponseModel
import com.example.a22housexam2.Networking.Model.TotalPcInfo
import com.example.a22housexam2.Networking.NetworkingInterface.GitHubApi
import com.example.a22housexam2.Networking.NetworkingInterface.SpringServerRequestTotal
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

object PcTotalInfoPresenter {
    lateinit var compositeDisposable: CompositeDisposable
    val TAG : String = "Pc Total Info Presenter"

    fun getTotalPcInfo() {
        Log.d(TAG,"Button Clicked!")
        println("Button Clicked")
        compositeDisposable = CompositeDisposable()

        compositeDisposable.addAll(
            SpringServerRequestTotal.getPcTotalInfo()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ response: TotalPcInfo ->
                    for( item in response.items){
                        // Gson 처리 로직
                        Log.d(TAG,item.id)
                        Log.d(TAG,item.cpu_data)
                        Log.d(TAG,item.ram_data)
                        Log.d(TAG,item.name)
                        Log.d(TAG,item.start_time)
                        Log.d(TAG,item.end_time)
                    }
                    //resultView.text = response.items[0].home_url
                }, { error: Throwable ->
                    Log.d(TAG, error.localizedMessage)
                })
        )
    }
}