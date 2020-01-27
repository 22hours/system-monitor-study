package com.example.a22housexam2.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.a22housexam2.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_pc_info.*
import kotlinx.android.synthetic.main.bottom_sheet.*
import kotlinx.android.synthetic.main.main_content.*

class PcInfoActivity : AppCompatActivity() {
    private lateinit var sheetBehavior: BottomSheetBehavior<LinearLayout>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_pc_info)

        val intent = intent
//        var tx_pc_id = findViewById<TextView>(R.id.pc_id)
//        var tx_pc_name = findViewById<TextView>(R.id.pc_name)
//        var tx_pc_power_status = findViewById<TextView>(R.id.pc_power_status)
//        var tx_pc_start_time = findViewById<TextView>(R.id.pc_start_time)
//        var tx_pc_end_time = findViewById<TextView>(R.id.pc_end_time)
//        var tx_pc_ram_data = findViewById<TextView>(R.id.pc_ram_data)
//        var tx_pc_cpu_data = findViewById<TextView>(R.id.pc_cpu_data)
//
//        tx_pc_id.text = intent.getStringExtra("pc_id")
//        tx_pc_name.text = intent.getStringExtra("pc_name")
//        tx_pc_power_status.text = intent.getStringExtra("pc_power_status")
//        tx_pc_start_time.text = intent.getStringExtra("pc_start_time")
//        tx_pc_end_time.text = intent.getStringExtra("pc_end_time")
//        tx_pc_ram_data.text = intent.getStringExtra("pc_ram_data")
//        tx_pc_cpu_data.text = intent.getStringExtra("pc_cpu_data")

    }

}
