package com.example.github.igenius

import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserManager @Inject constructor(){
    lateinit var username : String
    lateinit var token : String
}