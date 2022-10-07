package com.example.studyjetpack

import android.app.Application
import com.example.studyjetpack.data.dao.WordRepository
import com.example.studyjetpack.data.dao.WordRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class WordsApplication:Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy {
        WordRoomDatabase.getDatabase(this,applicationScope)
    }

    val repository by lazy {
        WordRepository(database.wordDao())
    }

    override fun onCreate() {
        super.onCreate()
    }
}