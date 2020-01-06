package com.example.a22housexam2.Activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.widget.Toast
import com.example.a22housexam2.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        login_button.setOnClickListener{
            Toast.makeText(this,"안녕하세요",Toast.LENGTH_SHORT).show()

            var intent = Intent(this, MainViewActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}
