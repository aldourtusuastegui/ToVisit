package com.acsoft.tovisit.data.remote

import com.acsoft.tovisit.data.model.InterviewList

class RemoteInterviewDataSource(private val webService: WebService) {

    suspend fun getInterviews() : InterviewList {
        return webService.getInterviews()
    }

}