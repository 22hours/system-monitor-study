package com.example.a22housexam2

import com.example.a22housexam2.ViewAdapter.ViewItem.PcInfo_FullItem

interface ConstantInterface {
    interface View_HomeFragment{
        fun notifyChanged()
    }

    interface View_RoomInfoActivity{
        fun notifyChanged()
        fun getRoomNumber() : String
    }

    interface View_PcInfoActivity{
        fun notifyChanged()
        fun getPcId() : String
    }

    interface Presenter_HomeFragement{
        fun getList()
    }

    interface Presenter_PcInfoActivity{
        fun getNowPcStatus() : PcInfo_FullItem
    }
}