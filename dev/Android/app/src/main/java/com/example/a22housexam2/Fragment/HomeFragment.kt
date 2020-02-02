package com.example.a22housexam2.Fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a22housexam2.ConstantInterface
import com.example.a22housexam2.DataManager.PcListManager.pcInfoCardViewList
import com.example.a22housexam2.DataManager.RoomListManager.RoomCardViewList
import com.example.a22housexam2.DataManager.RoomListManager.addPcItem
import com.example.a22housexam2.DataManager.RoomListManager.attachRoomObserver
import com.example.a22housexam2.DataManager.RoomListManager.dettachRoomObserver
import com.example.a22housexam2.Networking.NetworkingInterface.PcRequest
import com.example.a22housexam2.Networking.Service.PcRequestManager
import com.example.a22housexam2.R
import com.example.a22housexam2.Service.NetworkService.isNowTotal
import com.example.a22housexam2.ViewAdapter.PcRecyclerAdapter
import com.example.a22housexam2.ViewAdapter.RoomRecyclerAdapter
import com.example.a22housexam2.ViewAdapter.ViewItem.PcInfo_FullItem
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.main_view.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class HomeFragment : Fragment(), ConstantInterface.View_HomeFragment, CoroutineScope{
    private var TAG = "Home Fragment"
    private var llm = LinearLayoutManager(context)
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : RoomRecyclerAdapter

    //coroutine
    private lateinit var myJob : Job
    private var handler = CoroutineExceptionHandler{
        coroutineContext, throwable -> Log.e("Exception", ":" + throwable)
    }
    override val coroutineContext: CoroutineContext
        get() = myJob + Dispatchers.Main

    override fun notifyChanged() {
        Log.e(TAG, "Notify Changed")
        adapter = RoomRecyclerAdapter(activity, RoomCardViewList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        myJob = Job()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false) as ViewGroup
        isNowTotal = true
        attachRoomObserver(this)
       // list.clear()

        recyclerView = rootView.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.setHasFixedSize(true)
        adapter = RoomRecyclerAdapter(activity, RoomCardViewList)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        Log.e(TAG, "OnCreateView!")

        recyclerView.adapter = adapter

        launch(handler){
            while(true) {
                checkFlagText.setTextColor(Color.RED)
                print(Thread.currentThread().name)
                Log.d("코루틴 연습", "코루틴 메세지입니다 #########")
                Log.d("코루틴 연습", isNowTotal.toString())
                delay(5000L)
                PcRequestManager.requestPc()
                checkFlagText.setTextColor(Color.GRAY)
            }
        }
        return rootView
    }

    override fun onDestroy() {
        dettachRoomObserver(this)
        super.onDestroy()
        myJob.cancel()
    }
}