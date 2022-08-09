package com.homework.mymvp.main

import com.github.terrakok.cicerone.Router
import com.homework.mymvp.core.IScreens
import moxy.MvpPresenter


class MainPresenter(private val router: Router,private val screens: IScreens): MvpPresenter<MainView>() {

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        router.replaceScreen(screens.users())
    }

    fun onBackPressed() {
        router.exit()
    }
}