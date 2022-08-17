package com.homework.mymvp.users

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import com.homework.mymvp.R
import com.homework.mymvp.core.App
import com.homework.mymvp.core.OnBackPressedListener
import com.homework.mymvp.databinding.FragmentUsersBinding
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class UsersFragment : MvpAppCompatFragment(),UsersView, OnBackPressedListener{

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.button.setOnClickListener { permissionCheck() }
    }

    fun permissionCheck() {
        when {
            ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED -> {
               getGallery()
            }

            shouldShowRequestPermissionRationale(Manifest.permission.WRITE_EXTERNAL_STORAGE) -> {
                showAlertDialog(
                    getString(R.string.write_explanation),
                    getString(R.string.read_explanation)
                )
            }
            else -> {
                runPermissionRequest()
            }
        }
    }


    private val permissionsRequest = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        when {
            permissions.getOrDefault(Manifest.permission.READ_EXTERNAL_STORAGE, false) -> {
                  getGallery()
            }
            permissions.getOrDefault(Manifest.permission.WRITE_EXTERNAL_STORAGE, false) -> {
                getGallery()
            } else -> {
            showAlertDialog(
                getString(R.string.read_explanation), getString(R.string.write_explanation)
            )
        }
        }
    }
    private fun runPermissionRequest(){
        permissionsRequest.launch(arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE))
    }
    fun showAlertDialog(
        titleText: String,
        messageText: String
    ) {

        AlertDialog.Builder(
            requireContext()
        )
            .setTitle(titleText)
            .setMessage(messageText)
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                runPermissionRequest()
            }
            .setNegativeButton(getString(R.string.no)) { dialogInterface, _ ->
                dialogInterface.dismiss()
            }
            .create()
            .show()
    }


    private fun  getGallery(){
        Toast.makeText(requireContext(),"Открываю галерею", Toast.LENGTH_LONG).show()
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
