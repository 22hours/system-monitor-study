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
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.main_view.*

class MainViewActivity  : AppCompatActivity() {

    private lateinit var binding : MainViewBinding

    val TAG : String = "MainViewActivity"
    lateinit var compositeDisposable: CompositeDisposable

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.main_view)
        binding.resultView.setText("기존 TExt")
        testButton.setOnClickListener {
            Log.d(TAG,"Button Clicked!")
            println("Button Clicked")
            compositeDisposable = CompositeDisposable()

            compositeDisposable.addAll(GitHubApi.getRepoList(binding.researchIdText.text.toString())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe({ response: GitHubResponseModel ->
                    Toast.makeText(this, "Success!", Toast.LENGTH_LONG)
                    Log.d(TAG,response.items[0].id.toString())
                    Log.d(TAG,response.items[0].login)
                    Log.d(TAG,response.items[0].html_url)
                    binding.resultView.setText(response.items[0].html_url)
                     //resultView.text = response.items[0].home_url
                }, { error: Throwable ->
                    Log.d(TAG, error.localizedMessage)
                    Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_LONG)
                        .show()
                })
            )
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable.dispose()
    }
}