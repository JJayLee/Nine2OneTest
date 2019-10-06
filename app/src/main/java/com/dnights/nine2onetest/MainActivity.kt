package com.dnights.nine2onetest

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private var isReverseMode = false

    private val timeThread by lazy {
        Thread(Runnable {
            run {
                while(true){
                    val getTime = SimpleDateFormat("HH:mm:ss").format(Date(System.currentTimeMillis()))

                    Handler(Looper.getMainLooper()).post {
                        tv_time.text = getTime
                        val seconds = getTime.split(":")[2].toInt()
                        setTimeTextColor(seconds)
                    }

                    SystemClock.sleep(500)
                }
            }
        })
    }

    private fun setTimeTextColor(seconds: Int) {
        if (seconds % 2 == 0) {
            if(isReverseMode){
                tv_time.setTextColor(Color.BLUE)
                return
            }

            tv_time.setTextColor(Color.RED)
        }else{
            if(isReverseMode){
                tv_time.setTextColor(Color.RED)
                return
            }

            tv_time.setTextColor(Color.BLUE)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timeThread.start()

        button_change.setOnClickListener {
            isReverseMode = !isReverseMode
        }

    }

    override fun onDestroy() {
        timeThread.interrupt()
        super.onDestroy()
    }

}
