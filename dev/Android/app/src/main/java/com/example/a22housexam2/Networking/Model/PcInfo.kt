package com.example.a22housexam2.Networking.Model

import com.google.gson.annotations.SerializedName

class PcInfo {
    @SerializedName("classId")
    var classId : String = ""

    @SerializedName("powerStatus")
    var powerStatus : String? = ""

    @SerializedName("id")
    var id : String? = ""

    @SerializedName("name")
    var name : String? =""

    @SerializedName("cpuData")
    var cpuData : String? = ""

    @SerializedName("ramData")
    var ramData : String? = ""

    @SerializedName("startTime")
    var startTime : String? = ""

    @SerializedName("endTime")
    var endTime : String? = ""
}