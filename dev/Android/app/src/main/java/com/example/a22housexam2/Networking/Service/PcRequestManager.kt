package com.example.a22housexam2.Networking.Service

import android.util.Log
import android.widget.Toast
import com.example.a22housexam2.Networking.Model.PcInfo
import com.example.a22housexam2.Networking.NetworkingInterface.PcRequest
import com.example.a22housexam2.Presenter.PcTotalInfoPresenter.pcInfoCardViewList
import com.example.a22housexam2.ViewAdapter.ViewItem.PcInfo_FullItem
import com.example.a22housexam2.ViewAdapter.ViewItem.PcInfo_SmallItem
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class PcRequestManager {
    lateinit var compositeDisposable: CompositeDisposable

    public fun requestPc() {
        Log.d("Pc","Start!")
        var _response : PcInfo = PcInfo()
        compositeDisposable = CompositeDisposable()

        compositeDisposable.add(PcRequest.getPc1Info()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({ response: PcInfo ->
                Log.d("Pc","Success!")
                Log.d("Pc",response.id)
                if(response.name == null || response.name == "null"){
                    response.name = "none"
                }
                Log.d("Pc",response.name)
                Log.d("Pc",response.cpu_data)
                Log.d("Pc",response.ram_data)
                Log.d("Pc",response.start_time)
                Log.d("Pc",response.end_time)
                Log.d("Pc",response.power_status)

                _response.id = response.id
                _response.name = response.name
                _response.power_status = response.power_status
                _response.cpu_data = response.cpu_data
                _response.ram_data = response.ram_data
                _response.start_time = response.start_time
                _response.end_time = response.end_time

                Log.d("Pc response",_response.name)
                Log.d("Pc response",_response.cpu_data)
                Log.d("Pc response",_response.ram_data)
                Log.d("Pc response",_response.start_time)
                Log.d("Pc response",_response.end_time)
                Log.d("Pc response",_response.power_status)



                var listItem = PcInfo_SmallItem(
                    response.id,
                    response.name,
                    response.power_status,
                    response.start_time,
                    response.end_time
                )
                pcInfoCardViewList.add(listItem)
            }, { error: Throwable ->
                Log.d("Pc", error.localizedMessage)
            }))
        //Thread.sleep(2000)

        //<editor-fold desc="PC Request 2">

        compositeDisposable.add(PcRequest.getPc2Info()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({ response: PcInfo ->
                Log.d("Pc","Success!")
                Log.d("Pc",response.id)
                if(response.name == null || response.name == "null"){
                    response.name = "none"
                }
                Log.d("Pc",response.name)
                Log.d("Pc",response.cpu_data)
                Log.d("Pc",response.ram_data)
                Log.d("Pc",response.start_time)
                Log.d("Pc",response.end_time)
                Log.d("Pc",response.power_status)

                _response.id = response.id
                _response.name = response.name
                _response.power_status = response.power_status
                _response.cpu_data = response.cpu_data
                _response.ram_data = response.ram_data
                _response.start_time = response.start_time
                _response.end_time = response.end_time

                Log.d("Pc response",_response.name)
                Log.d("Pc response",_response.cpu_data)
                Log.d("Pc response",_response.ram_data)
                Log.d("Pc response",_response.start_time)
                Log.d("Pc response",_response.end_time)
                Log.d("Pc response",_response.power_status)



                var listItem = PcInfo_SmallItem(
                    response.id,
                    response.name,
                    response.power_status,
                    response.start_time,
                    response.end_time
                )
                pcInfoCardViewList.add(listItem)
            }, { error: Throwable ->
                Log.d("Pc", error.localizedMessage)
            }))
        //</editor-fold>


        //<editor-fold desc="PC Request 3">
        compositeDisposable.add(PcRequest.getPc3Info()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.newThread())
            .subscribe({ response: PcInfo ->
                Log.d("Pc","Success!")
                Log.d("Pc",response.id)
                if(response.name == null || response.name == "null"){
                    response.name = "none"
                }
                Log.d("Pc",response.name)
                Log.d("Pc",response.cpu_data)
                Log.d("Pc",response.ram_data)
                Log.d("Pc",response.start_time)
                Log.d("Pc",response.end_time)
                Log.d("Pc",response.power_status)

                _response.id = response.id
                _response.name = response.name
                _response.power_status = response.power_status
                _response.cpu_data = response.cpu_data
                _response.ram_data = response.ram_data
                _response.start_time = response.start_time
                _response.end_time = response.end_time

                Log.d("Pc response",_response.name)
                Log.d("Pc response",_response.cpu_data)
                Log.d("Pc response",_response.ram_data)
                Log.d("Pc response",_response.start_time)
                Log.d("Pc response",_response.end_time)
                Log.d("Pc response",_response.power_status)



                var listItem = PcInfo_SmallItem(
                    response.id,
                    response.name,
                    response.power_status,
                    response.start_time,
                    response.end_time
                )
                pcInfoCardViewList.add(listItem)
            }, { error: Throwable ->
                Log.d("Pc", error.localizedMessage)
            }))
        //</editor-fold>

    }
}



