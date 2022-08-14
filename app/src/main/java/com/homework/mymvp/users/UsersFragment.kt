package com.homework.mymvp.users

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.homework.mymvp.R
import com.homework.mymvp.core.App
import com.homework.mymvp.core.OnBackPressedListener
import com.homework.mymvp.databinding.FragmentUsersBinding
import com.homework.mymvp.repository.GithubUserRepo
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment : MvpAppCompatFragment(),UsersView, OnBackPressedListener {

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!

    private var adapter: UsersRVAdapter? = null
    private val presenter: UsersPresenter by moxyPresenter {
        UsersPresenter(GithubUserRepo(), App.INSTANCE.router)
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

    override fun init() {
        adapter = UsersRVAdapter(presenter.usersListPresenter)
        with(binding){
            rvUsers.layoutManager = LinearLayoutManager(context)
            rvUsers.adapter = adapter
        }
    }

    override fun updateList() {
        adapter?.notifyDataSetChanged()
    }

    override fun showProgressBar() {
      binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgressBar() {
        binding.progressBar.visibility = View.GONE
    }

    override fun showToast() {
        Toast.makeText(requireContext(),getString(R.string.error_toast),Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}
