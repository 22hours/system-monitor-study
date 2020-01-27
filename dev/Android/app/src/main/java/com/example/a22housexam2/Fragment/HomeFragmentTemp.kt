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
import com.example.a22housexam2.DataManager.PcListManager.pcInfoCardViewList
import com.example.a22housexam2.R
import com.example.a22housexam2.ViewAdapter.PcRecyclerAdapter
import com.example.a22housexam2.ViewAdapter.ViewItem.PcInfo_FullItem


class HomeFragmentTemp : Fragment(){
    private var llm = LinearLayoutManager(context)
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : PcRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val rootView = inflater.inflate(R.layout.fragment_home, container, false) as ViewGroup

        // list.clear()
        recyclerView = rootView.findViewById(R.id.recycler_view) as RecyclerView
        recyclerView.setHasFixedSize(true)
        adapter = PcRecyclerAdapter(activity, pcInfoCardViewList)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        Log.e("Frag", "MainFragment")

//        recyclerView = (RecyclerView) rootView.
//        recycler_view.adapter = RecyclerAdapter(activity.applicationContext,items)
//        recycler_view.setHasFixedSize(true)
//        recycler_view.layoutManager= llm
//
//        var PcInfo_SmallItem = PcInfo_SmallItem(
//            pcinfo.id,
//            pcinfo.name,
//            pcinfo.power_status,
//            pcinfo.start_time,
//            pcinfo.end_time)
//
//        list.add(PcInfo_SmallItem)


//        return inflater.inflate(R.layout.fragment_home, container, false)

        var bindingTestButton = rootView.findViewById<Button>(R.id.bindingTest)
        bindingTestButton.setOnClickListener {
            var PcInfo_FullItem = PcInfo_FullItem(
                "바인딩실험",
                "바인딩실험이름",
                "바인딩전원",
                "바인딩 cpu",
                "바인딩 램",
                "바인딩 시작시간",
                "바인딩 끝시간"
            )
            pcInfoCardViewList.add(PcInfo_FullItem)
            recyclerView.adapter = adapter
        }
        recyclerView.adapter = adapter
        return rootView
    }
}