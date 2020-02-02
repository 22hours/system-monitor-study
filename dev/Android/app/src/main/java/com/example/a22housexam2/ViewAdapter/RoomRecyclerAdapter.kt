package com.example.a22housexam2.ViewAdapter

import com.example.a22housexam2.ViewAdapter.ViewItem.Room_item


import com.example.a22housexam2.ViewAdapter.ViewItem.PcInfo_SmallItem
import android.content.Context
import android.content.Intent
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a22housexam2.ViewAdapter.ViewItem.Recycler_item
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import androidx.databinding.ObservableArrayList
import com.example.a22housexam2.R
import androidx.fragment.app.FragmentActivity
import com.example.a22housexam2.Activities.PcInfoActivity
import com.example.a22housexam2.ViewAdapter.ViewItem.PcInfo_FullItem
import androidx.core.content.ContextCompat.startActivity
import android.R.id
import com.example.a22housexam2.Activities.RoomInfoActivity
import com.example.a22housexam2.Service.NetworkService.isNowTotal


class RoomRecyclerAdapter : RecyclerView.Adapter<RoomRecyclerAdapter.ViewHolder>{
    var context: Context?
    var items: ObservableArrayList<Room_item>
    constructor(context: FragmentActivity?, items: ObservableArrayList<Room_item>){
        this.context = context
        this.items = items
    }

    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_room_card, null)
        return ViewHolder(v)
    }

    @Override
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item : Room_item = items.get(position)
        val pos = position
        holder.classId.text = item.classId
        holder.rcv.setOnClickListener{
            var intent : Intent
            intent = Intent(context,RoomInfoActivity::class.java)
            intent.putExtra("classId",item.classId)
            isNowTotal = false;
            context?.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = this.items.size

    class ViewHolder constructor (itemView : View)
        : RecyclerView.ViewHolder(itemView){
        val classId = itemView.findViewById<TextView>(R.id.classId)
        val rcv = itemView.findViewById<CardView>(R.id.rcv)
    }

}