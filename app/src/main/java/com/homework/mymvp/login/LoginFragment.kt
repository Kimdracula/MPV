package com.homework.mymvp.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.homework.mymvp.KEY_BUNDLE
import com.homework.mymvp.core.App
import com.homework.mymvp.core.OnBackPressedListener
import com.homework.mymvp.databinding.FragmentLoginBinding
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter


class LoginFragment : MvpAppCompatFragment(), LoginView, OnBackPressedListener {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val presenter: LoginPresenter by moxyPresenter {
        LoginPresenter(App.INSTANCE.router)
    }
    private var param: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param = it.getString(KEY_BUNDLE)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvLogin.text = param
    }

    companion object {
        fun newInstance(param: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_BUNDLE, param)
                }
            }
    }

    override fun onBackPressed(): Boolean = presenter.backPressed()


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}