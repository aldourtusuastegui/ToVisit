package com.acsoft.tovisit.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.acsoft.tovisit.data.model.InterviewItemEntity

@Database(entities = [InterviewItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun interviewDao(): InterviewDao

    companion object {

        private var INSTANCE: AppDatabase? = null

        fun  getDatabase(context: Context) : AppDatabase {
            INSTANCE = INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "interviews")
                .build()

            return  INSTANCE!!
        }

        fun destroyInstance() {
            INSTANCE = null
        }

    }

}