package com.homework.mymvp.users

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class UsersPresenter(private val router: Router): MvpPresenter<UsersView>() {


    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}