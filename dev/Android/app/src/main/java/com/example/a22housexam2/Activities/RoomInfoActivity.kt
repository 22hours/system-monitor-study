package com.example.a22housexam2.Activities

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.a22housexam2.ConstantInterface
import com.example.a22housexam2.DataManager.PcListManager
import com.example.a22housexam2.DataManager.RoomListManager.RoomHashMap
import com.example.a22housexam2.DataManager.RoomListManager.attachRoomInfoObserver
import com.example.a22housexam2.DataManager.RoomListManager.dettachRoomObserver
import com.example.a22housexam2.Networking.Service.PcRequestManager.context
import com.example.a22housexam2.R
import com.example.a22housexam2.Service.NetworkService.isNowTotal
import com.example.a22housexam2.ViewAdapter.PcRecyclerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_room_info.*

class RoomInfoActivity : AppCompatActivity(), ConstantInterface.View_RoomInfoActivity{
    private var llm = LinearLayoutManager(context)
    private lateinit var recyclerView : RecyclerView
    private lateinit var adapter : PcRecyclerAdapter
    private var roomNumber : String = ""

    override fun getRoomNumber(): String {
        return this.roomNumber
    }

    override fun notifyChanged(){
        adapter = PcRecyclerAdapter(this, RoomHashMap[roomNumber]!!)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_room_info)
        val intent = intent
        this.roomNumber = intent.getStringExtra("classId")

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.setHasFixedSize(true)

        classId.text = intent.getStringExtra("classId")

        var backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }

        attachRoomInfoObserver(this)
        notifyChanged()
    }

    override fun onOptionsItemSelected(item : MenuItem) : Boolean{
        when(item.itemId){
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        isNowTotal = true
        super.onBackPressed()
    }



    override fun onDestroy() {

        super.onDestroy()
    }
}