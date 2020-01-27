package com.example.a22housexam2.Networking.Model

import com.google.gson.annotations.SerializedName

class TotalPcInfo{
    @SerializedName("total")
    val total : Int = 0

    @SerializedName("pcs")
    val pcs:List<PcInfo> = listOf()
}