package com.blogspot.codesgram.backgroundthreads

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import com.blogspot.codesgram.backgroundthreads.databinding.ActivityMainBinding
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var bindingMain : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        bindingMain = ActivityMainBinding.inflate(layoutInflater)

        setContentView(bindingMain.root)

        val btnStart = bindingMain.btnStart
        val tvStatus = bindingMain.tvStatus
        val tvHidden = bindingMain.hiddenTv

        tvHidden.visibility = View.GONE

        val executor = Executors.newSingleThreadExecutor()
        val handler = Handler(Looper.getMainLooper())

        btnStart.setOnClickListener{
            executor.execute{
                try{

                    //simulate process in background thread
                    for (i in 0..10){
                        Thread.sleep(500)
                        val percentage = i * 10
                        handler.post{
                            //update the UI here
                            if (percentage == 100){
                                tvStatus.text = getString(R.string.task_completed)
                                tvHidden.visibility = View.VISIBLE
                            }else{
                                tvStatus.text = String.format(getString(R.string.compressing), percentage)
                                tvHidden.visibility = View.GONE
                            }
                        }

                    }
                }catch (e : Exception){
                    e.printStackTrace()
                }
            }
        }
    }
}