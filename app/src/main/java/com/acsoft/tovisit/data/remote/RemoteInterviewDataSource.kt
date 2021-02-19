package com.acsoft.tovisit.data.remote

import com.acsoft.tovisit.data.model.InterviewItem

class RemoteInterviewDataSource(private val webService: WebService) {

    suspend fun getInterviews() : List<InterviewItem> {
        return webService.getInterviews()
    }

}