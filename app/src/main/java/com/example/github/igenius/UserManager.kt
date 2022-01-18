package com.example.github.igenius

import com.example.github.igenius.githubrepositories.data.dto.UserDTO
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserManager @Inject constructor(){
    lateinit var displayName: String
    lateinit var uid: String
    lateinit var token : String
    lateinit var user : UserDTO

    fun isTokenInitialized() = ::token.isInitialized
    fun isUserInitialized() = ::user.isInitialized
}