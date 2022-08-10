package com.homework.mymvp.users

import com.github.terrakok.cicerone.Router
import com.homework.mymvp.core.Screens
import com.homework.mymvp.model.GithubUser
import com.homework.mymvp.repository.GithubUserRepo
import moxy.MvpPresenter

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
        viewState.init()
        loadData()
        usersListPresenter.itemClickListener = { itemView ->
router.navigateTo(Screens.login("Жопа"))

        }

    }

    private fun loadData() {
        val users = usersRepo.getUsers()
        usersListPresenter.users.addAll(users)
        viewState.updateList()
    }

    fun backPressed(): Boolean {
        router.exit()
        return true
    }

}