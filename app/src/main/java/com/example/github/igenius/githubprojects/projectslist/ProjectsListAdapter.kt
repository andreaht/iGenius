package com.example.github.igenius.githubprojects.projectslist

import com.example.github.igenius.R
import com.udacity.project4.base.BaseRecyclerViewAdapter


//Use data binding to show the reminder on the item
class ProjectsListAdapter(callBack: (selectedProject: ProjectDataItem) -> Unit) :
    BaseRecyclerViewAdapter<ProjectDataItem>(callBack) {
    override fun getLayoutRes(viewType: Int) = R.layout.it_project
}