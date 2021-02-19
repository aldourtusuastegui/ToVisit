package com.acsoft.tovisit.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.acsoft.tovisit.data.model.InterviewItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface InterviewDao {

    @Query("SELECT * FROM InterviewItemEntity")
    fun getInterviews() : Flow<List<InterviewItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInterviews(interviewItemEntity: InterviewItemEntity)

}