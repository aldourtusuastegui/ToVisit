package com.acsoft.tovisit.data.remote

import com.acsoft.tovisit.application.AppConstants
import com.acsoft.tovisit.data.model.InterviewItemData
import com.google.gson.GsonBuilder
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface WebService {

    @GET(AppConstants.API_INTERVIEW)
    suspend fun getInterviews() : Response<List<InterviewItemData>>

}

object RetrofitClient {

    val webService: WebService by lazy {
        Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)
    }
}