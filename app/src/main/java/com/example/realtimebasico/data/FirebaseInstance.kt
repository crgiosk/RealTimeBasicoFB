package com.example.realtimebasico.data

import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import kotlin.random.Random
import kotlin.random.nextInt

class FirebaseInstance(context: Context) {

    private val database = Firebase.database

    init {
        FirebaseApp.initializeApp(context)
    }

    fun writeOnFirebase() {
        val ref = database.reference
        ref.setValue("Mi primera escritura ${Random.nextInt(1..10)}")
    }
}