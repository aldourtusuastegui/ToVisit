package com.acsoft.tovisit.repository

import com.acsoft.tovisit.data.model.InterviewItemEntity
import kotlinx.coroutines.flow.Flow

interface InterviewRepository {

    suspend fun getInterviews() : Flow<List<InterviewItemEntity>>

    suspend fun getVisitsToDo() : Flow<Int>

    fun searchInterview(street : String) : Flow<List<InterviewItemEntity>>

}