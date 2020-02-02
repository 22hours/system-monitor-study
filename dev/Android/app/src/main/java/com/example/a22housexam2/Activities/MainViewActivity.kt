package com.example.a22housexam2.Activities

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.a22housexam2.Networking.Model.GitHubResponseModel
import com.example.a22housexam2.Networking.NetworkingInterface.GitHubApi
import com.example.a22housexam2.R
import com.example.a22housexam2.databinding.MainViewBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_view.*
import android.view.MenuItem
import androidx.annotation.NonNull
import androidx.databinding.adapters.TextViewBindingAdapter.setText
import android.R.attr.fragment
import androidx.fragment.app.Fragment
import com.example.a22housexam2.Fragment.HomeFragment
import com.example.a22housexam2.Fragment.MenuFragment
import com.example.a22housexam2.Fragment.SettingFragment
import com.example.a22housexam2.Networking.NetworkingInterface.PcRequest
import com.example.a22housexam2.Networking.Service.PcRequestManager
import com.example.a22housexam2.Utility.Utils
import com.example.a22housexam2.ViewAdapter.ViewItem.PcInfo_FullItem
import kotlinx.android.synthetic.main.main_view_page.*


class MainViewActivity  : AppCompatActivity() {
    val homeFrag  = HomeFragment()
    val menuFrag = MenuFragment()
    val settingFragment = SettingFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_view_page)
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.message)
        bottomNavigationView.setOnNavigationItemSelectedListener(
            object : BottomNavigationView.OnNavigationItemSelectedListener {
                override fun onNavigationItemSelected(@NonNull item: MenuItem): Boolean {
                    // 어떤 메뉴 아이템이 터치되었는지 확인합니다.
                    when (item.itemId) {
                        R.id.home -> {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.container,homeFrag)
                                .commit()
                            return true
                        }
                        R.id.menu -> {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.container,menuFrag)
                                .commit()
                            return true
                        }
                        R.id.setting -> {
                            supportFragmentManager.beginTransaction()
                                .replace(R.id.container,settingFragment)
                                .commit()
                            return true
                        }
                    }
                    return false
                }
            })
    }

}