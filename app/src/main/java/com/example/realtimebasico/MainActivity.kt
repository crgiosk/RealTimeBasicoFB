package com.example.realtimebasico

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.WindowManager.LayoutParams
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.realtimebasico.data.FirebaseInstance
import com.example.realtimebasico.databinding.ActivityMainBinding
import com.example.realtimebasico.databinding.DialogNewTaskBinding
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.addTask -> showDialog()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun showDialog() {
        val binding = DialogNewTaskBinding.inflate(layoutInflater)
        val dialog = Dialog(this)
        binding.addTaskDialogButton.setOnClickListener {
            val tile = binding.titleTaskTextView.text.toString()
            val description = binding.descriptionTaskTextView.text.toString()
            if (tile.isEmpty() or description.isEmpty()) {
                Toast.makeText(this, "Not empty values allowed", Toast.LENGTH_LONG).show()
            } else {
                firebaseInstance.createTask(
                    title = tile,
                    description = description,
                )
                dialog.dismiss()
            }
        }
        dialog.setContentView(binding.root)
        dialog.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        dialog.show()
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
        taskAdapter = TaskAdapter { reference: String ->
            firebaseInstance.removeFromDatabase(reference)
        }
        binding.recyclerViewTask.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            adapter = taskAdapter
        }
    }
}