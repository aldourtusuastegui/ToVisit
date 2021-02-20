package com.acsoft.tovisit.presentation

import androidx.lifecycle.*
import com.acsoft.tovisit.repository.InterviewRepository
import kotlinx.coroutines.launch

class InterviewViewModel(private val repo: InterviewRepository) : ViewModel() {

    private var updated : Boolean = false

    fun saveUpdated(updated: Boolean) {
        this.updated = updated
    }

    fun getUpdated(): Boolean {
        return updated
    }

    suspend fun getInterviewsListApi() = repo.getInterviewsApi().asLiveData()

    fun getInterviewsListLocal() = repo.getInterviewsLocal().asLiveData()

    fun getVisitsToDo() = repo.getVisitsToDo().asLiveData()

    fun searchInterview(interview: String) = repo.searchInterview(interview).asLiveData()

    fun updateAccount(streetName : String, isVisited : Boolean) {
        viewModelScope.launch {
            repo.setVisited(streetName, isVisited)
        }
    }


}

class InterviewModelFactory(private val repo: InterviewRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(InterviewRepository::class.java).newInstance(repo)
    }
}