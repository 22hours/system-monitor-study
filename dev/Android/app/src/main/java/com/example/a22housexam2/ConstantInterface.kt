package com.example.a22housexam2

interface ConstantInterface {
    interface View_HomeFragment{
        fun notifyChanged()
    }

    interface View_RoomInfoActivity{
        fun notifyChanged()
        fun getRoomNumber() : String
    }

    interface Presenter_HomeFragement{
        fun getList()
    }
}