package com.example.studyjetpack.data.dao

import androidx.annotation.WorkerThread
import com.example.studyjetpack.data.Word
import kotlinx.coroutines.flow.Flow

class WordRepository(private val wordDao: WordDao) {
    val allWords: Flow<List<Word>> = wordDao.getAlphabetizedWords()


    @WorkerThread
    suspend fun insert(word: Word) {
        wordDao.insert(word)
    }
}