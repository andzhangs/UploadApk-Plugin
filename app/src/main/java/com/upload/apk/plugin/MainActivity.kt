package com.upload.apk.plugin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.upload.apk.plugin.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Example of a call to a native method
        binding.sampleText.text = stringFromJNI()
    }
    external fun stringFromJNI(): String

    companion object {
        init {
            System.loadLibrary("plugin")
        }
    }
}