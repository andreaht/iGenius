package com.example.github.igenius.githubrepositories.repositorieslist

import com.example.github.igenius.R
import com.udacity.project4.base.BaseRecyclerViewAdapter


//Use data binding to show the reminder on the item
class RepositoriesListAdapter(callBack: (selectedRepository: RepositoryDataItem) -> Unit) :
    BaseRecyclerViewAdapter<RepositoryDataItem>(callBack) {
    override fun getLayoutRes(viewType: Int) = R.layout.it_project
}