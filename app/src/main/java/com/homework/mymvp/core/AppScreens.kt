package com.homework.mymvp.core

import com.github.terrakok.cicerone.Screen
import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.homework.mymvp.users.UsersFragment

class AppScreens: IScreens {
    override fun users(): Screen {
        return  FragmentScreen { UsersFragment.newInstance() }
    }
}