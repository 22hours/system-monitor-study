package com.example.a22housexam2.Networking.Model

import com.google.gson.annotations.SerializedName

class PcInfo {
    @SerializedName("id")
    var id : String = ""

    @SerializedName("name")
    var name : String =""

    @SerializedName("power_status")
    var power_status : Boolean = false

    @SerializedName("cpu_data")
    var cpu_data : String = ""

    @SerializedName("ram_data")
    var ram_data : String = ""

    @SerializedName("start_time")
    var start_time : String = ""

    @SerializedName("end_time")
    var end_time : String = ""
}