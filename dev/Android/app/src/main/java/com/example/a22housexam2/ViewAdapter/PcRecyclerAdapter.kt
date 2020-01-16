package com.example.a22housexam2.ViewAdapter


import com.example.a22housexam2.ViewAdapter.ViewItem.PcInfo_SmallItem
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a22housexam2.ViewAdapter.ViewItem.Recycler_item
import android.view.LayoutInflater
import androidx.databinding.ObservableArrayList
import com.example.a22housexam2.R
import androidx.fragment.app.FragmentActivity


class PcRecyclerAdapter : RecyclerView.Adapter<PcRecyclerAdapter.ViewHolder>{
    var context: Context?
    var items: ObservableArrayList<PcInfo_SmallItem>
    constructor(context: FragmentActivity?, items: ObservableArrayList<PcInfo_SmallItem>){
        this.context = context
        this.items = items
    }

    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_pc_card, null)
        return ViewHolder(v)
    }

    @Override
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item : PcInfo_SmallItem = items.get(position)
        holder.pc_id.setText(item.pc_id)
        holder.pc_name.setText(item.pc_name)
        holder.pc_power_status.setText(item.pc_power_status)
        holder.pc_start_time.setText(item.pc_start_time)
        holder.pc_end_time.setText(item.pc_end_time)
    }

    override fun getItemCount(): Int = this.items.size

    class ViewHolder constructor (itemView : View)
        : RecyclerView.ViewHolder(itemView){
        val pc_id = itemView.findViewById<TextView>(R.id.pc_id)
        val pc_name = itemView.findViewById<TextView>(R.id.pc_name)
        val pc_power_status = itemView.findViewById<TextView>(R.id.pc_status)
        val pc_start_time = itemView.findViewById<TextView>(R.id.pc_start_time)
        val pc_end_time = itemView.findViewById<TextView>(R.id.pc_end_time)

    }

}