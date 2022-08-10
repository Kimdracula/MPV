package com.homework.mymvp.repository

import com.homework.mymvp.model.GithubUser

class GithubUserRepo {
    private val repositories = listOf(
        GithubUser("login1"),
        GithubUser("login2"),
        GithubUser("login3"),
        GithubUser("login4"),
        GithubUser("login5")
    )

    fun getUsers() : List<GithubUser> {
        return repositories
    }


}