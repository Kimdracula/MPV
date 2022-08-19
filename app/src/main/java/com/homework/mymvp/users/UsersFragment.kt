package com.homework.mymvp.users

import android.Manifest
import android.app.AlertDialog
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.drawToBitmap
import com.homework.mymvp.R
import com.homework.mymvp.core.App
import com.homework.mymvp.core.OnBackPressedListener
import com.homework.mymvp.databinding.FragmentUsersBinding
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class UsersFragment : MvpAppCompatFragment(),UsersView, OnBackPressedListener{

    private var _binding: FragmentUsersBinding? = null
    private val binding get() = _binding!!
    private var uri: Uri? = null

    private val loadImage = registerForActivityResult(ActivityResultContracts.GetContent()) {
        binding.imageView.setImageURI(it)
        uri = it

    }

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
        binding.buttonChoose.setOnClickListener { permissionCheck()
        }
        binding.buttonConvert.setOnClickListener {
           convertToPng()
        }
    }

    private fun convertToPng() {
        val bitmap = binding.imageView.drawToBitmap()
        var file: File? = null
        val uriPathHelper = URIPathHelper()
        val filePath = uri?.let { uriPathHelper.getPath(requireContext(), it) }
        file = File(filePath +File.separator + "fileNameToSave.png")
file.createNewFile()



        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, bos) // YOU can also save it in JPEG
        val bitMapData = bos.toByteArray()

        val fos = FileOutputStream(file)
        fos.write(bitMapData)
        fos.flush()
        fos.close()


        /*
      val path = requireContext().filesDir.absolutePath
       val compressedPictureFile = File(path+ uri?.encodedPath.toString())
        val bitmap = BitmapFactory.decodeFile(path+ uri?.encodedPath.toString())
        val fOut: FileOutputStream = FileOutputStream(compressedPictureFile)
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, fOut)
        fOut.flush()
        fOut.close()
*/
    }

    private fun permissionCheck() {
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
    private fun showAlertDialog(
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

            loadImage.launch("image/*")

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
