package com.example.realtimebasico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.realtimebasico.data.FirebaseInstance
import com.example.realtimebasico.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    private lateinit var firebaseInstance: FirebaseInstance
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        firebaseInstance = FirebaseInstance(this)

        setupUI()
        setupFirebaseListener()
    }

    private fun setupFirebaseListener() {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val data = snapshot.value

                binding.result.text = (data ?: "Vacio").toString()
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(TAG, "onCancelled: ${error.details}")
            }

        }
        firebaseInstance.setListener(listener)
    }

    private fun setupUI() {
        binding.btnUpdate.setOnClickListener {
            firebaseInstance.writeOnFirebase()
        }
    }
}