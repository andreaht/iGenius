package com.example.github.igenius.githubrepositories.repositorieslist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.github.igenius.githubrepositories.data.GithubRepository
import com.example.github.igenius.githubrepositories.data.dto.RepositoryDTO
import com.example.github.igenius.githubrepositories.data.dto.Result
import com.udacity.project4.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class RepositoriesListViewModel @Inject constructor(
    app: Application,
    private val repository: GithubRepository
) : BaseViewModel(app) {
    // list that holds the reminder data to be displayed on the UI
    val reposList = MutableLiveData<List<RepositoryDataItem>>()

    /**
     * Get all the reminders from the DataSource and add them to the remindersList to be shown on the UI,
     * or show error if any
     */
    fun loadLocalRepos() {
        showLoading.value = true
        viewModelScope.launch {
            //interacting with the dataSource has to be through a coroutine
            val result = repository.getRepos()
            showLoading.postValue(false)
            when (result) {
                is Result.Success<*> -> {
                    val dataList = ArrayList<RepositoryDataItem>()
                    dataList.addAll((result.data as List<RepositoryDTO>).map { project ->
                        //map the project data from the DB to the be ready to be displayed on the UI
                        RepositoryDataItem(
                            project.name,
                            project.description,
                            project.language,
                            project.star
                        )
                    })
                    reposList.value = dataList
                }
                is Result.Error ->
                    showSnackBar.value = result.message
            }

            //check if no data has to be shown
            invalidateShowNoData()
        }
    }

    /**
     * Inform the user that there's not any data if the remindersList is empty
     */
    private fun invalidateShowNoData() {
        showNoData.value = projectsList.value == null || projectsList.value!!.isEmpty()
    }
}