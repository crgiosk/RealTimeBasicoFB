package com.example.realtimebasico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.realtimebasico.data.FirebaseInstance
import com.example.realtimebasico.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private lateinit var firebaseInstance: FirebaseInstance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseInstance = FirebaseInstance(this)

        setupUI()
    }

    private fun setupUI() {
        binding.btnUpdate.setOnClickListener {
            firebaseInstance.writeOnFirebase()
        }
    }
}