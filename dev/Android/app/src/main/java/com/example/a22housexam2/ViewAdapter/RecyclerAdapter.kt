package com.example.a22housexam2.ViewAdapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.a22housexam2.ViewAdapter.ViewItem.Recycler_item
import android.view.LayoutInflater
import com.example.a22housexam2.R
import androidx.fragment.app.FragmentActivity


class RecyclerAdapter : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{
    var context: Context?
    var items: List<Recycler_item>
    constructor(context: FragmentActivity?, items: List<Recycler_item>){
        this.context = context
        this.items = items
    }

    @Override
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v : View = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cardview, null)
        return ViewHolder(v)
    }

    @Override
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var item : Recycler_item = items.get(position)
        holder.tv_title.setText(item.tv_title)
        holder.tv_date.setText(item.tv_date)
        holder.tv_content.setText(item.tv_content)
        holder.tv_count.setText(item.tv_count)
        holder.tv_writer.setText(item.tv_writer)
    }

    override fun getItemCount(): Int = this.items.size

    class ViewHolder constructor (itemView : View)
        : RecyclerView.ViewHolder(itemView){
        val tv_title = itemView.findViewById<TextView>(R.id.tv_title)
        val tv_date  = itemView.findViewById<TextView>(R.id.tv_date)
        val tv_content  = itemView.findViewById<TextView>(R.id.tv_content)
        val tv_count  = itemView.findViewById<TextView>(R.id.tv_count)
        val tv_writer  = itemView.findViewById<TextView>(R.id.tv_writer)
    }

}