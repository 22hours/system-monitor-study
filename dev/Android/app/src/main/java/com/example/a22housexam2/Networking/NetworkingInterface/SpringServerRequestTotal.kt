package com.example.a22housexam2.Networking.NetworkingInterface

import com.example.a22housexam2.Networking.RetrofitCreator.RetrofitCreator
import com.example.a22housexam2.Networking.Model.TotalPcInfo
import io.reactivex.Observable
import retrofit2.http.GET

class SpringServerRequestTotal{
    interface SpringServerRequestTotalImpl{
        @GET("/root")
        fun getPcTotalInfo() : Observable<TotalPcInfo>
    }

    companion object{
        fun getPcTotalInfo() : Observable<TotalPcInfo> {
            return RetrofitCreator.create(SpringServerRequestTotalImpl::class.java).getPcTotalInfo()
        }
    }
}