package com.homework.mymvp.users

import com.github.terrakok.cicerone.Router
import com.homework.mymvp.core.Screens
import com.homework.mymvp.model.GithubUser
import com.homework.mymvp.random
import com.homework.mymvp.repository.GithubUserRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers
import moxy.MvpPresenter
import java.util.concurrent.TimeUnit

class UsersPresenter(private val usersRepo: GithubUserRepo, private val router: Router) :
    MvpPresenter<UsersView>() {


    class UsersListPresenter : IUserListPresenter {
        val users = mutableListOf<GithubUser>()

        override var itemClickListener: ((UserItemView) -> Unit)? = null

        override fun bindView(view: UserItemView) {
            val user = users[view.pos]
            view.setLogin(user.login)
        }

        override fun getCount(): Int = users.size
    }

    val usersListPresenter = UsersListPresenter()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.showProgressBar()
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = { itemView ->
            router.navigateTo(Screens.login(usersListPresenter.users[itemView.pos].login))
        }
    }

    private fun loadData() {
        usersRepo.getUsers()
            .delay(random, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                usersListPresenter.users.addAll(it)
                viewState.updateList()
                viewState.hideProgressBar()
            }
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }
}