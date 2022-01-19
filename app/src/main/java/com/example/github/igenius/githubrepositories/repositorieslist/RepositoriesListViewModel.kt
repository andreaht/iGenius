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
    // list that holds the repository data to be displayed on the UI
    val reposList = MutableLiveData<List<RepositoryDataItem>>()

    /**
     * Get all the repositories from the DataSource and add them to the remindersList to be shown on the UI,
     * or show error if any
     */
    private fun loadLocalRepos() {
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
     * Inform the user that there's not any data if the repositoriesList is empty
     */
    private fun invalidateShowNoData() {
        showNoData.value = reposList.value == null || reposList.value!!.isEmpty()
    }

    fun loadRepositories() {

        showLoading.value = true
        viewModelScope.launch {
            //interacting with the dataSource has to be through a coroutine
            val result = repository.getRemoteRepos()
            showLoading.postValue(false)
            when (result) {
                is Result.Success<*> -> {
                    for(repo in (result.data as List<RepositoryDTO>)){
                        repository.saveRepo(repo)
                    }
                }
                is Result.Error ->
                    showSnackBar.value = result.message
            }

            loadLocalRepos()
        }
    }

    //functions to selected repository info
    private val _showRepositoryInfo = MutableLiveData<RepositoryDataItem>()
    val showRepositoryInfo
        get() = _showRepositoryInfo

    private val _showRepositoryInfoStarred = MutableLiveData<Boolean>()
    val showRepositoryInfoStarred
        get() = _showRepositoryInfoStarred

    fun onRepositoryClicked(repository: RepositoryDataItem) {
        _showRepositoryInfo.value = repository
        getRepositoryStarred(repository.title)
    }

    fun starRepository(repositoryName: String) {
        viewModelScope.launch {
            if(_showRepositoryInfoStarred.value == true)
                repository.setRepositoryNotStarred(repositoryName)
            else
                repository.setRepositoryStarred(repositoryName)

            //refresh stars count
            _showRepositoryInfoStarred.value = !_showRepositoryInfoStarred.value!!
            loadRepositories()
        }
    }

    private fun getRepositoryStarred(repositoryName: String) {
        viewModelScope.launch {
            when (val result = repository.getRepositoryStarred(repositoryName)) {
                is Result.Success<*> ->
                    _showRepositoryInfoStarred.value = (result.data as Boolean)
                is Result.Error ->
                    showSnackBar.value = result.message
            }
        }
    }
}