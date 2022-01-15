package com.example.github.igenius.githubprojects.projectslist

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

/**
 * data class acts as a data mapper between the DB and the UI
 */
@Parcelize
data class ProjectDataItem(
    var title: String?,
    var description: String?,
    var language: String?,
    var star: Number?
) : Parcelable