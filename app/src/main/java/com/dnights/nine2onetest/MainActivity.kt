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

    val timeThread by lazy {
        Thread(Runnable {
            run {
                while(true){
                    val now = System.currentTimeMillis()
                    val date = Date(now)
                    val sdf = SimpleDateFormat("HH:mm:ss")
                    val getTime = sdf.format(date)

                    Handler(Looper.getMainLooper()).post {
                        tv_time.text = getTime

                        val seconds = getTime.split(":")[2].toInt()

                        if(isReverseMode){
                            if(seconds % 2 ==0) {
                                tv_time.setTextColor(Color.BLUE)
                            }else{
                                tv_time.setTextColor(Color.RED)
                            }
                        }else{
                            if(seconds % 2 ==0) {
                                tv_time.setTextColor(Color.RED)
                            }else{
                                tv_time.setTextColor(Color.BLUE)
                            }
                        }
                    }

                    SystemClock.sleep(500)
                }
            }
        })
    }

    var isReverseMode = false

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
