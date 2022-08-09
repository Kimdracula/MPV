package com.homework.mymvp.main

import com.github.terrakok.cicerone.Router
import com.homework.mymvp.core.Screens.users
import moxy.MvpPresenter


class MainPresenter(private val router: Router): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(users())
    }

    fun onBackPressed() {
        router.exit()
    }
}