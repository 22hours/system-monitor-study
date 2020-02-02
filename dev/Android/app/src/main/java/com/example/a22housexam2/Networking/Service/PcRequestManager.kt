package com.example.a22housexam2.Networking.Service

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import android.widget.Toast
import com.example.a22housexam2.Networking.NetworkingInterface.PcRequest
import com.example.a22housexam2.DataManager.PcListManager.pcInfoCardViewList
import com.example.a22housexam2.ViewAdapter.ViewItem.PcInfo_FullItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import com.example.a22housexam2.App
import com.example.a22housexam2.DataManager.RoomListManager.addPcItem
import com.example.a22housexam2.DataManager.RoomListManager.addPcItemTest
import com.example.a22housexam2.Networking.Model.TotalPcInfo


@SuppressLint("StaticFieldLeak")
object PcRequestManager {
    lateinit var compositeDisposable: CompositeDisposable
    var context = App.applicationContext()

    fun isNetworkConnected(context: Context): Boolean{
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork : NetworkInfo? = cm.activeNetworkInfo
        val isConnected = activeNetwork != null && activeNetwork.isConnected
        return isConnected
    }

    public fun requestPc() {
        Log.d("Pc","Start!")
        compositeDisposable = CompositeDisposable()
        compositeDisposable.add(PcRequest.getPc1Info()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({ response: TotalPcInfo ->
                Log.d("Pc","Success!")
                for ( item in response.pcs){
                    try {
                        var listItem: PcInfo_FullItem = PcInfo_FullItem(
                            item.classId,
                            item.id,
                            item.name,
                            item.powerStatus,
                            item.cpuData,
                            item.ramData,
                            item.startTime,
                            item.endTime
                        )
                        Log.d("PC Request", item.classId)
                        Log.d("PC Request", item.id)
                        Log.d("PC Request", item.name)
                        Log.d("PC Request", item.powerStatus)
                        Log.d("PC Request", item.ramData)
                        Log.d("PC Request", item.cpuData)
                        Log.d("PC Request", item.startTime)
                        Log.d("PC Request", item.endTime)
                        addPcItemTest(listItem)
                    }
                    catch (e : Exception){
                        Log.d("PC Request"," Item is NULL")
                        continue
                    }
                }
                Toast.makeText(context,"Card 추가 완료!",Toast.LENGTH_LONG);

            }, { error: Throwable ->
                Log.d("PC REQUEST", error.localizedMessage)
                Log.d("PC REQUEST", "=============================== GET ERROR =================================")
                Toast.makeText(context,"Network Error",Toast.LENGTH_LONG);
            }))
        //Thread.sleep(2000)
        Log.d("Pc response", "End")

    }
}



