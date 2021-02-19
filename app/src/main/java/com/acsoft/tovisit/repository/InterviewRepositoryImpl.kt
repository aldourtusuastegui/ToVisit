package com.acsoft.tovisit.repository

import com.acsoft.tovisit.data.model.InterviewItem
import com.acsoft.tovisit.data.remote.RemoteInterviewDataSource

class InterviewRepositoryImpl(private val dataSourceRemote : RemoteInterviewDataSource) : InterviewRepository {

    override suspend fun getInterviews(): List<InterviewItem> {
        return dataSourceRemote.getInterviews()
    }

}