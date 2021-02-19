package com.acsoft.tovisit.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.acsoft.tovisit.core.Resource
import com.acsoft.tovisit.repository.InterviewRepository
import kotlinx.coroutines.Dispatchers
import java.lang.Exception

class InterviewViewModel(private val repo: InterviewRepository) : ViewModel() {

    fun getInterviews() = liveData(Dispatchers.IO) {
        emit(Resource.Loading())
        try {
            emit(Resource.Success((repo.getInterviews())))
        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

}

class InterviewModelFactory(private val repo: InterviewRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(InterviewRepository::class.java).newInstance(repo)
    }
}