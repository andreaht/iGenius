package com.example.github.igenius.githubrepositories.repositorieslist

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


/**
 * data class acts as a data mapper between the DB and the UI
 */
@Parcelize
data class RepositoryDataItem(
    var title: String,
    var description: String?,
    var language: String?,
    var star: Int?,
    var isPrivate: Boolean
) : Parcelable