package com.acsoft.tovisit.data.local

import com.acsoft.tovisit.data.model.InterviewItemEntity
import kotlinx.coroutines.flow.Flow

class LocalInterviewDataSource(private val interviewDao: InterviewDao) {

    fun getInterviews() : Flow<List<InterviewItemEntity>> {
        return interviewDao.getInterviews()
    }

    fun getVisitsToDo() : Flow<Int> {
        return interviewDao.getVisitsToDo()
    }

    fun searchInterview(street : String) : Flow<List<InterviewItemEntity>> {
        return interviewDao.searchInterview(street)
    }

    suspend fun saveInterviews(interviewItem: InterviewItemEntity) {
        interviewDao.saveInterviews(interviewItem)
    }
}