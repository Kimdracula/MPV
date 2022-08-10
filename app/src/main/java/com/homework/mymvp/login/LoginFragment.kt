package com.homework.mymvp.login

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.mymvp.R
import com.homework.mymvp.core.App
import com.homework.mymvp.core.OnBackPressedListener
import com.homework.mymvp.databinding.FragmentLoginBinding
import com.homework.mymvp.databinding.FragmentUsersBinding
import com.homework.mymvp.repository.GithubUserRepo
import com.homework.mymvp.users.UsersFragment
import com.homework.mymvp.users.UsersPresenter
import com.homework.mymvp.users.UsersRVAdapter
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class LoginFragment : MvpAppCompatFragment(), LoginView, OnBackPressedListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val app = App()

    private val presenter: LoginPresenter by moxyPresenter {
        LoginPresenter(app.router)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    companion object {
        fun newInstance(): LoginFragment {
            return LoginFragment()
        }
    }

    override fun onBackPressed(): Boolean = presenter.backPressed()


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}