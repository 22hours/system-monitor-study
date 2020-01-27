package com.example.a22housexam2.ViewAdapter


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
import android.app.AlertDialog


class PcRecyclerAdapter : RecyclerView.Adapter<PcRecyclerAdapter.ViewHolder>{
    var context: Context?
    var items: ObservableArrayList<PcInfo_FullItem>
    constructor(context: FragmentActivity?, items: ObservableArrayList<PcInfo_FullItem>){
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
        var item : PcInfo_FullItem = items.get(position)
        val pos = position
        holder.pc_id.text = item.id
        holder.pc_name.text = item.name
        holder.pc_power_status.text = item.power_status
        holder.pc_start_time.text = item.start_time
        holder.pc_end_time.text = item.end_time
        holder.cv.setOnClickListener{
                var intent : Intent
                intent = Intent(context,PcInfoActivity::class.java)
                intent.putExtra("pc_id",item.id)
                intent.putExtra("pc_name",item.name)
                intent.putExtra("pc_power_status",item.power_status)
                intent.putExtra("pc_start_time",item.start_time)
                intent.putExtra("pc_end_time",item.end_time)
                intent.putExtra("pc_ram_data",item.ram_data)
                intent.putExtra("pc_cpu_data",item.cpu_data)
                context?.startActivity(intent)
            }
    }

    override fun getItemCount(): Int = this.items.size

    class ViewHolder constructor (itemView : View)
        : RecyclerView.ViewHolder(itemView){
        val pc_id = itemView.findViewById<TextView>(R.id.pc_id)
        val pc_name = itemView.findViewById<TextView>(R.id.pc_name)
        val pc_power_status = itemView.findViewById<TextView>(R.id.pc_status)
        val pc_start_time = itemView.findViewById<TextView>(R.id.pc_start_time)
        val pc_end_time = itemView.findViewById<TextView>(R.id.pc_end_time)
        val cv = itemView.findViewById<CardView>(R.id.cv)
    }

}