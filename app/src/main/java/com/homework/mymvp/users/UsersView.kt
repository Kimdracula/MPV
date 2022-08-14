package com.homework.mymvp.users

import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.StateStrategyType

@StateStrategyType(AddToEndSingleStrategy::class)
interface UsersView:MvpView{
    fun init()
    fun updateList()
    fun showProgressBar()
    fun hideProgressBar()
}