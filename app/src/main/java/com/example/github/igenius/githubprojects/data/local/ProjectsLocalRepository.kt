package com.example.github.igenius.githubprojects.data.local

import com.example.github.igenius.githubprojects.data.ProjectDataSource
import com.example.github.igenius.githubprojects.data.dto.ProjectDTO
import com.example.github.igenius.githubprojects.data.dto.Result
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProjectsLocalRepository @Inject constructor() : ProjectDataSource {
    override suspend fun getProjects(): Result<List<ProjectDTO>> {
        TODO("Not yet implemented")
    }

    override suspend fun saveProject(project: ProjectDTO) {
        TODO("Not yet implemented")
    }

    override suspend fun getProject(id: String): Result<ProjectDTO> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteAllProjects() {
        TODO("Not yet implemented")
    }
}