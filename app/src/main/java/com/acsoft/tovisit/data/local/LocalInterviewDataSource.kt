package com.acsoft.tovisit.data.local

import com.acsoft.tovisit.data.model.InterviewItemEntity
import kotlinx.coroutines.flow.Flow

class LocalInterviewDataSource(private val interviewDao: InterviewDao) {

    fun getInterviews() : Flow<List<InterviewItemEntity>> {
        return interviewDao.getInterviews()
    }

    suspend fun saveInterviews(interviewItem: InterviewItemEntity) {
        interviewDao.saveInterviews(interviewItem)
    }
}