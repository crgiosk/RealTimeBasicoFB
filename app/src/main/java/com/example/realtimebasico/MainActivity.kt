package com.example.realtimebasico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimebasico.data.FirebaseInstance
import com.example.realtimebasico.databinding.ActivityMainBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var binding: ActivityMainBinding

    private lateinit var firebaseInstance: FirebaseInstance

    private lateinit var taskAdapter: TaskAdapter
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
                val data = getTaskFromDataSnapShot(snapshot)
                taskAdapter.updateList(data)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.i(TAG, "onCancelled: ${error.details}")
            }

        }
        firebaseInstance.setListener(listener)
    }

    private fun getTaskFromDataSnapShot(snapshot: DataSnapshot): List<Pair<String, Task>> {
        val elements = snapshot.children.map {
            Pair(it.key!!, it.getValue(Task::class.java)!!)
        }
        return elements
    }

    private fun setupUI() {
        binding.btnUpdate.setOnClickListener {
            firebaseInstance.writeOnFirebase()
        }
        taskAdapter = TaskAdapter {reference: String ->
            firebaseInstance.removeFromDatabase(reference)
        }
        binding.recyclerViewTask.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = taskAdapter
        }
    }
}