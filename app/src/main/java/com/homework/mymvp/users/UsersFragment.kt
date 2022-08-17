package com.homework.mymvp.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.homework.mymvp.core.App
import com.homework.mymvp.core.OnBackPressedListener
import com.homework.mymvp.databinding.FragmentUsersBinding
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment : MvpAppCompatFragment(),UsersView, OnBackPressedListener {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    private val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter( App.INSTANCE.router)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUsersBinding.inflate(inflater, container, false)
        return binding.root
    }


    companion object {
        fun newInstance(): UsersFragment {
            return UsersFragment()
        }
    }

    override fun onBackPressed(): Boolean = presenter.backPressed()


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
