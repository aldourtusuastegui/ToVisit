package com.acsoft.tovisit.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.lifecycle.asLiveData
import com.acsoft.tovisit.core.Resource
import com.acsoft.tovisit.repository.InterviewRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class InterviewViewModel(private val repo: InterviewRepository) : ViewModel() {


    suspend fun getInterviewsList() = repo.getInterviews().asLiveData()

    suspend fun getVisitsToDo() = repo.getVisitsToDo().asLiveData()

}

class InterviewModelFactory(private val repo: InterviewRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(InterviewRepository::class.java).newInstance(repo)
    }
}