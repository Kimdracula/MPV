package com.homework.mymvp.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.homework.mymvp.R
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment : MvpAppCompatFragment(),UsersView {

    private val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_users, container, false)
    }


    companion object {
        fun newInstance(): UsersFragment {
            return UsersFragment()
        }
    }
}
