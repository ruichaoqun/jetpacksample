package com.example.studyjetpack.data.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.studyjetpack.data.Word
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

//Room 数据库类必须是 abstract 且扩展 RoomDatabase.
@Database(entities = arrayOf(Word::class), version = 1, exportSchema = false)
public abstract class WordRoomDatabase : RoomDatabase() {
    abstract fun wordDao(): WordDao

    private class WordDatabaseCallback(
        private val scope: CoroutineScope
    ):RoomDatabase.Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE ?.let {database->
                scope.launch {
                    populateDatabase(database.wordDao())
                }
            }
        }

        suspend fun populateDatabase(wordDao: WordDao) {
            wordDao.deleteAll()

            var word = Word("hello")
            wordDao.insert(word)
            word = Word("world!")
            wordDao.insert(word)
        }
    }

    companion object{
        private var INSTANCE:WordRoomDatabase? =null

        //定义单例WordRoomDatabase
        fun getDatabase(context: Context,scope:CoroutineScope):WordRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,WordRoomDatabase::class.java,"word_database")
                    .addCallback(WordDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}