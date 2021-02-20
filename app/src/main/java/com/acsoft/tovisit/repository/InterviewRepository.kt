package com.acsoft.tovisit.repository

import com.acsoft.tovisit.data.model.InterviewItemEntity
import kotlinx.coroutines.flow.Flow

interface InterviewRepository {

    suspend fun getInterviewsApi() : Flow<List<InterviewItemEntity>>

    fun getInterviewsLocal() : Flow<List<InterviewItemEntity>>

    fun getVisitsToDo() : Flow<Int>

    fun searchInterview(street : String) : Flow<List<InterviewItemEntity>>

    suspend fun setVisited(streetName: String, isVisited: Boolean)

}