package com.upload.apk.plugin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.metrics.performance.JankStats
import androidx.metrics.performance.PerformanceMetricsState
import com.upload.apk.plugin.databinding.ActivityMainBinding

/**
 * <a href="https://www.pgyer.com/doc/view/api#commonParams">蒲公英官方文档</a>
 *
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()


        val listener = JankStats.OnFrameListener {
            Log.i(
                "print_logs",
                "当前帧开始时间: ${it.frameStartNanos}\n" +
                        "当前帧耗时：${it.frameDurationUiNanos}\n" +
                        "是否发生了卡顿：${it.isJank}\n" +
                        "业务代码记录的UI状态：${it.states}"
            )
        }

        val jankStats = JankStats.createAndTrack(window, listener)
        jankStats.isTrackingEnabled = true

        //通过PerformanceMetricsState在关键业务代码处埋点
        val state=PerformanceMetricsState.getHolderForHierarchy(binding.sampleText).state

        Log.i("print_logs", "state: $state")
    }

    external fun stringFromJNI(): String

    companion object {
        init {
            System.loadLibrary("plugin")
        }
    }
}