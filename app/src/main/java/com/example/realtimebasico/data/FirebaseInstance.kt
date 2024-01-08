package com.example.realtimebasico.data

import android.content.Context
import com.example.realtimebasico.Task
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.random.Random
import kotlin.random.nextInt

class FirebaseInstance(context: Context) {

    private val database = Firebase.database
    val ref = database.reference

    init {
        FirebaseApp.initializeApp(context)
    }

    fun writeOnFirebase() {
        ///ref.setValue("Mi primera escritura ${Random.nextInt(1..10)}")
        //.push crea un nuevo index para el siguiente elemento de la bd en firebase
        val newItem = ref.push()
        newItem.setValue(
            getGenericTodoTaskItem(Random.nextInt(1..10))
        )
    }

    fun setListener(listener: ValueEventListener) {
        database.reference.addValueEventListener(listener)
    }

    private fun getGenericTodoTaskItem(number: Int) = Task(
        title = "Tarea",
        description = "Numero $number"
    )

    fun removeFromDatabase(reference: String) {
        ref.child(reference).removeValue()
    }
}