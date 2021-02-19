package com.acsoft.tovisit.repository

import com.acsoft.tovisit.data.model.InterviewList
import com.acsoft.tovisit.data.remote.RemoteInterviewDataSource

class InterviewRepositoryImpl(private val dataSourceRemote : RemoteInterviewDataSource) : InterviewRepository {

    override suspend fun getInterviews(): InterviewList {
        return dataSourceRemote.getInterviews()
    }

}