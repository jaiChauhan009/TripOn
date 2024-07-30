package com.example.tripon

import android.app.Application
import com.example.tripon.database.NoteDatabase

// MyApp.kt
class MyApplication : Application() {
    val database: NoteDatabase by lazy {
        NoteDatabase.getDatabase(this)
    }
    val myDatabase:AppDatabase by lazy {
        AppDatabase.getDatabase(this)
    }
}
