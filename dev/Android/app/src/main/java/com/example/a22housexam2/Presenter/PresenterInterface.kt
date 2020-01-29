package com.example.a22housexam2.Presenter

interface PresenterInterface {
    interface LoadPresenter{
        fun getInternetState()
    }

    interface DashBoardPresenter{
        fun getClassCard()
    }

    interface ClassViewPresenter{
        fun getPcCard()
    }

    interface PcInfoPresenter{
        fun getTotalPcInfo()
    }

}