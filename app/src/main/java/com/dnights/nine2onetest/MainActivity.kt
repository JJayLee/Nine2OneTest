package com.dnights.nine2onetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class MainActivity : AppCompatActivity() {

    private val compositeDisposable = CompositeDisposable()

    private var isReverseMode = false

    private fun setTimeTextColor(seconds: Int) {
        val red = ContextCompat.getColorStateList(this@MainActivity,R.color.textRed)
        val blue = ContextCompat.getColorStateList(this@MainActivity,R.color.textBlue)

        if (seconds % 2 == 0) {
            if(isReverseMode){
                tv_time.setTextColor(blue)
                return
            }

            tv_time.setTextColor(red)
        }else{
            if(isReverseMode){
                tv_time.setTextColor(red)
                return
            }

            tv_time.setTextColor(blue)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Observable.interval(100,TimeUnit.MILLISECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                val getTime = SimpleDateFormat("HH:mm:ss").format(Date(System.currentTimeMillis()))
                tv_time.text = getTime
                val seconds = getTime.split(":")[2].toInt()
                setTimeTextColor(seconds)
            }.apply {
                compositeDisposable.add(this)
            }

        button_change.setOnClickListener {
            isReverseMode = !isReverseMode
        }

    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

}
