package com.example.github.igenius.githubrepositories.data.dto

data class UserDTO(
    val gists_url : String?,
    val repos_url : String?,
    val following_url : String?,
    val twitter_username : String?,
    val bio : String?,
    val created_at : String?,
    val login : String,
    val type : String?,
    val blog : String?,
    val subscriptions_url : String?,
    val updated_at : String?,
    val site_admin : Boolean,
    val company : String?,
    val id : Int,
    val public_repos : Int,
    val gravatar_id : String?,
    val email : String?,
    val organizations_url : String?,
    val hireable : String?,
    val starred_url : String?,
    val followers_url : String?,
    val public_gists : Int,
    val url : String?,
    val received_events_url : String?,
    val followers : Int,
    val avatar_url : String?,
    val events_url : String?,
    val html_url : String?,
    val following : Int,
    val name : String?,
    val location : String?,
    val node_id : String?
)
