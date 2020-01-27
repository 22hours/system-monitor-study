package com.example.a22housexam2.Fragment

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
import com.example.a22housexam2.R
import com.example.a22housexam2.ViewAdapter.PcRecyclerAdapter
import com.example.a22housexam2.ViewAdapter.RoomRecyclerAdapter
import com.example.a22housexam2.ViewAdapter.ViewItem.PcInfo_FullItem
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.main_view.*


class HomeFragment : Fragment(), ConstantInterface.View_HomeFragment{
    private var TAG = "Home Fragment"
    private var llm = LinearLayoutManager(context)
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : RoomRecyclerAdapter

    override fun notifyChanged() {
        Log.e(TAG, "Notify Changed")
        adapter = RoomRecyclerAdapter(activity, RoomCardViewList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false) as ViewGroup

        attachRoomObserver(this)
       // list.clear()

        recyclerView = rootView.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.setHasFixedSize(true)
        adapter = RoomRecyclerAdapter(activity, RoomCardViewList)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        Log.e(TAG, "OnCreateView!")

        recyclerView.adapter = adapter
        var button1 = rootView.findViewById<Button>(R.id.btButton)
        var button2 = rootView.findViewById<Button>(R.id.btButton2)

        button2.setOnClickListener{
            var testItem = PcInfo_FullItem(
                "405",
                "test2",
                "이정환",
                "on",
                "10",
                "10",
                "10:10:10",
                "10:10:10"
            )
            addPcItem(testItem)
        }
        button1.setOnClickListener{
            var testItem = PcInfo_FullItem(
                "404",
                "test1",
                "이정환",
                "on",
                "10",
                "10",
                "10:10:10",
                "10:10:10"
            )
            addPcItem(testItem)
        }
        return rootView
    }

    override fun onDestroy() {
        dettachRoomObserver(this)
        super.onDestroy()
    }
}