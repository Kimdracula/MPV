package com.homework.mymvp.core

import com.github.terrakok.cicerone.androidx.FragmentScreen
import com.homework.mymvp.users.UsersFragment

object Screens {
    fun users() = FragmentScreen { UsersFragment.newInstance() }
}