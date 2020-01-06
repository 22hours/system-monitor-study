package com.example.a22housexam2.Activities

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


abstract class BaseActivity : AppCompatActivity() {

    private var instance: BaseActivity? = null
    private var mToolbarHeight = 0
    private var mAnimDuration = 0

    private var mVaActionBar: ValueAnimator? = null

    protected abstract var viewId: Int
    protected abstract var toolbarId: Int?

    protected abstract fun onCreate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        instance = this

        setContentView(viewId)

        if (toolbarId != null)
            findViewById<Toolbar>(toolbarId!!).let {
                setSupportActionBar(it)
                supportActionBar?.setDisplayShowTitleEnabled(false)
            }
        onCreate()
    }

    fun hideActionBar() {
        toolbarId?.let {
            val mToolbar = findViewById<Toolbar>(toolbarId!!)

            if (mToolbarHeight == 0)
                mToolbarHeight = mToolbar.height

            if (mVaActionBar != null && mVaActionBar!!.isRunning)
                return@let

            mVaActionBar = ValueAnimator.ofInt(mToolbarHeight, 0)
            mVaActionBar?.addUpdateListener {
                ValueAnimator.AnimatorUpdateListener { animation ->
                    mToolbar.layoutParams.height = animation.animatedValue as Int
                    mToolbar.requestLayout()
                }
            }

            mVaActionBar?.addUpdateListener {
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        supportActionBar?.hide()
                    }
                }
            }

            mVaActionBar!!.duration = mAnimDuration.toLong()
            mVaActionBar?.start()

        }
    }

    fun showActionBar() {
        toolbarId?.let {
            val mToolbar = findViewById<Toolbar>(toolbarId!!)

            if (mVaActionBar != null && mVaActionBar!!.isRunning)
                return@let

            mVaActionBar = ValueAnimator.ofInt(0, mToolbarHeight)
            mVaActionBar?.addUpdateListener {
                ValueAnimator.AnimatorUpdateListener { animation ->
                    mToolbar.layoutParams.height = animation.animatedValue as Int
                    mToolbar.requestLayout()
                }
            }

            mVaActionBar?.addUpdateListener {
                object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator?) {
                        supportActionBar?.hide()
                    }
                }
            }

            mVaActionBar!!.duration = mAnimDuration.toLong()
            mVaActionBar?.start()

        }
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }
    // <- 버튼 누를 시 뒤로가기

}