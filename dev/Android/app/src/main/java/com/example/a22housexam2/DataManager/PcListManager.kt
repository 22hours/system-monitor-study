package com.example.a22housexam2.DataManager

import android.util.Log
import android.widget.Toast
import androidx.databinding.ObservableArrayList
import com.example.a22housexam2.Networking.Model.GitHubResponseModel
import com.example.a22housexam2.Networking.Model.PcInfo
import com.example.a22housexam2.Networking.Model.TotalPcInfo
import com.example.a22housexam2.Networking.NetworkingInterface.GitHubApi
import com.example.a22housexam2.Networking.NetworkingInterface.SpringServerRequestTotal
import com.example.a22housexam2.ViewAdapter.ViewItem.PcInfo_FullItem
import com.example.a22housexam2.ViewAdapter.ViewItem.PcInfo_SmallItem
import com.example.a22housexam2.ViewAdapter.ViewItem.Room_item
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

object PcListManager {
    lateinit var compositeDisposable: CompositeDisposable
    val TAG : String = "Pc Total Info Presenter"
    public var pcInfo = PcInfo()
    public var pcInfoCardViewList = ObservableArrayList<PcInfo_FullItem>()
}