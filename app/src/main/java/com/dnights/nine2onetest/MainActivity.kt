package com.dnights.nine2onetest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    val timeThread by lazy {
        TimeThread(tv_time)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        timeThread.start()
    }

    override fun onDestroy() {
        timeThread.interrupt()
        super.onDestroy()
    }


    class TimeThread(private val textView: TextView) : Thread() {
        override fun run() {
            super.run()
            while(true){
                val now = System.currentTimeMillis()
                val date = Date(now)
                val sdf = SimpleDateFormat("HH:mm:ss")
                val getTime = sdf.format(date)

                Handler(Looper.getMainLooper()).post {
                    textView.text = getTime
                }

                SystemClock.sleep(500)
            }

        }
    }

}
