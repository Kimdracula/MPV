package com.homework.mymvp.login

import com.github.terrakok.cicerone.Router
import moxy.MvpPresenter

class LoginPresenter(private val router: Router): MvpPresenter<LoginView>() {


    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}