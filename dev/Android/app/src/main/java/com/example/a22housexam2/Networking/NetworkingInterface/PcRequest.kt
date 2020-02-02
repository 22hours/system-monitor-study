package com.example.a22housexam2.Networking.NetworkingInterface

import com.example.a22housexam2.Networking.Model.PcInfo
import com.example.a22housexam2.Networking.RetrofitCreator.RetrofitCreator
import com.example.a22housexam2.Networking.Model.TotalPcInfo
import com.example.a22housexam2.Networking.RetrofitCreator.SpringServerRetrofitCreator
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

class PcRequest{
    interface PcRequestImpl1{
        @GET("root")
        fun getPc1Info() : Observable<TotalPcInfo>
    }

    interface PcRequestImpl2{
        @GET("pc/2")
        fun getPc2Info() : Observable<TotalPcInfo>
    }

    interface PcRequestImpl3{
        @GET("pc/3")
        fun getPc3Info() : Observable<TotalPcInfo>
    }

    companion object{
        fun getPc1Info() : Observable<TotalPcInfo> {
            return SpringServerRetrofitCreator.create(PcRequestImpl1::class.java).getPc1Info()
        }

        fun getPc2Info() : Observable<TotalPcInfo> {
            return SpringServerRetrofitCreator.create(PcRequestImpl2::class.java).getPc2Info()
        }

        fun getPc3Info() : Observable<TotalPcInfo> {
            return SpringServerRetrofitCreator.create(PcRequestImpl3::class.java).getPc3Info()
        }
    }
}