package com.example.a22housexam2.Activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageButton
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
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import com.example.a22housexam2.ConstantInterface
import com.example.a22housexam2.DataManager.RoomListManager.RoomHashMapTest
import com.example.a22housexam2.DataManager.RoomListManager.attachPcInfoObserver
import com.example.a22housexam2.DataManager.RoomListManager.dettachPcInfoObserver
import kotlinx.android.synthetic.main.activity_pc_info2.*


class PcInfoActivity : AppCompatActivity() , ConstantInterface.View_PcInfoActivity{
    private var nowPcId = ""
    private var nowClassId = ""
    override fun notifyChanged() {
        var item = RoomHashMapTest[this.nowClassId]!![this.nowPcId]
        pc_id.text  = item!!.id
        pc_name.text = item!!.name
        pc_start_time.text = item!!.start_time
        pc_end_time.text = item!!.end_time
        pc_cpu_data_int.text = item!!.cpu_data + "%"
        pc_ram_data_int.text = item!!.ram_data+ "%"
    }

    override fun getPcId(): String {
        return this.nowPcId
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.activity_pc_info2)
        val intent = intent
        nowClassId = intent.getStringExtra("pc_classId")!!
        nowPcId = intent.getStringExtra("pc_id")!!
        attachPcInfoObserver(this)
        notifyChanged()
        //pc_cpu_data_progressbar.setProgress(cpuData.toInt())
        //pc_ram_data_progressbar.setProgress(ramData.toInt())
//        pc_cpu_data_progressbar.progress = intent.getStringExtra("pc_cpu_data") as Int
 //       pc_ram_data_progressbar.progress = intent.getStringExtra("pc_ram_data") as Int

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
        //UI 객체생성

        //데이터 가져오
        var shutDownButton = findViewById<ImageButton>(R.id.shut_down_button)
        var delayButton = findViewById<ImageButton>(R.id.delay_button)
        var backButton = findViewById<ImageButton>(R.id.backButton)

        shutDownButton.setOnClickListener{
            Toast.makeText(this,"ShutDown 메세지를 전송합니다!",Toast.LENGTH_SHORT).show()
        }

        delayButton.setOnClickListener{
            Toast.makeText(this,"Delay 메세지를 전송합니다!",Toast.LENGTH_SHORT).show()
        }

        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onBackPressed() {
        dettachPcInfoObserver()
        super.onBackPressed()
    }
}
