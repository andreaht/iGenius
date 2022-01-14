package com.example.github.igenius.githubprojects.projectslist

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.github.igenius.githubprojects.data.ProjectDataSource
import com.example.github.igenius.githubprojects.data.dto.ProjectDTO
import com.example.github.igenius.githubprojects.data.dto.Result
import com.udacity.project4.base.BaseViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProjectsListViewModel @Inject constructor(
    app: Application,
    private val dataSource: ProjectDataSource
) : BaseViewModel(app) {
    // list that holds the reminder data to be displayed on the UI
    val projectsList = MutableLiveData<List<ProjectDataItem>>()

    /**
     * Get all the reminders from the DataSource and add them to the remindersList to be shown on the UI,
     * or show error if any
     */
    fun loadReminders() {
        showLoading.value = true
        viewModelScope.launch {
            //interacting with the dataSource has to be through a coroutine
            val result = dataSource.getProjects()
            showLoading.postValue(false)
            when (result) {
                is Result.Success<*> -> {
                    val dataList = ArrayList<ProjectDataItem>()
                    dataList.addAll((result.data as List<ProjectDTO>).map { project ->
                        //map the project data from the DB to the be ready to be displayed on the UI
                        ProjectDataItem(
                            project.title,
                            project.description,
                            project.location,
                            project.latitude,
                            project.longitude,
                            project.id
                        )
                    })
                    projectsList.value = dataList
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