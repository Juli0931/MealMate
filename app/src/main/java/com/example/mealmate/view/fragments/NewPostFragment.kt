package com.example.mealmate.view.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.viewModels
import com.example.mealmate.R
import com.example.mealmate.databinding.FragmentNewPostBinding
import com.example.mealmate.domain.model.RecipePost
import com.example.mealmate.view.state.UIState
import com.example.mealmate.view.util.ImageUtil
import com.example.mealmate.view.util.toStorageURL
import com.example.mealmate.viewmodel.NewPostViewModel
import com.google.type.DateTime
import java.io.File
import java.util.UUID

class NewPostFragment : Fragment(), PopupMenu.OnMenuItemClickListener {

    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        onGalleryResult(result)
    }
    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        onCameraResult(result)
    }
    private val cameraPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        onCameraPermissionResult(permissions)
    }
    private val galleryPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        onGalleryPermissionResult(isGranted)
    }

    private lateinit var binding:FragmentNewPostBinding
    private val viewModel:NewPostViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setupButtons()
        observeStates()


    }

    private fun observeStates() {
        viewModel.uiState.observe(viewLifecycleOwner){uiState ->
            when(uiState){
                UIState.WAITING -> {
                    binding.progressBar.visibility = View.GONE
                }
                UIState.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                }
                UIState.SUCCESS -> {
                    Toast.makeText(requireContext(),"Publicación exitosa",Toast.LENGTH_LONG).show()
                    activity?.onBackPressed()

                }
                UIState.ERROR -> {
                    Toast.makeText(requireContext(),"Ocurrió un error, intenta más tarde",Toast.LENGTH_LONG).show()
                    viewModel.uiState.postValue(UIState.WAITING)
                }
            }

        }

        viewModel.imageSelected.observe(viewLifecycleOwner){
            ImageUtil().renderImageCenterCrop(requireContext(), it, binding.postImage)
        }
    }

    private fun showPopup(v: View) {
        val popup = PopupMenu(requireContext(), v)
        popup.setOnMenuItemClickListener(this)
        popup.inflate(R.menu.media_menu)
        popup.show()
    }
    override fun onMenuItemClick(item: MenuItem?): Boolean {
        when (item!!.itemId) {
            R.id.action_camera -> {
                if(isCameraPermissionsGranted()){
                    openCamera()
                }else{
                    requestCameraPermissions()
                }
            }
            R.id.action_gallery -> {
                if(isGalleryPermissionsGranted()){
                    openGallery()
                }else{
                    requestGalleryPermissions()
                }
            }
        }
        return true
    }

    private fun isCameraPermissionsGranted(): Boolean {
        val permissions = mutableListOf(
            Manifest.permission.CAMERA
        ).toTypedArray()
        var allGranted = true
        permissions.forEach {
            allGranted = ContextCompat.checkSelfPermission(requireContext(), it) != PackageManager.PERMISSION_DENIED && allGranted
        }
        return allGranted
    }

    private fun isGalleryPermissionsGranted(): Boolean {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            return ContextCompat.checkSelfPermission(requireContext(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_DENIED
        }
        return true
    }
    private fun requestCameraPermissions() {
        cameraPermissionLauncher.launch(
            mutableListOf(
                Manifest.permission.CAMERA
            ).toTypedArray()
        )
    }

    private fun requestGalleryPermissions() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
        }
    }
    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = File("${activity?.getExternalFilesDir(null)}/${System.currentTimeMillis()}")
        val uri = FileProvider.getUriForFile(
            requireContext(),
            requireActivity().packageName,
            file
        )
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        viewModel.tempCameraImage.postValue(uri)
        cameraLauncher.launch(intent)
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        galleryLauncher.launch(intent)
    }

    private fun setupButtons() {
        binding.closePost.setOnClickListener{
            activity?.onBackPressed()
        }

        binding.postImage.setOnClickListener{
            showPopup(it)
        }

        binding.sharePost.setOnClickListener{
            viewModel.currentUser.value?.let {user ->
                val postImageURL = viewModel.recipePostId.value?.toStorageURL(requireContext())
                val newPost = RecipePost(
                    id = viewModel.recipePostId.value!!,
                    username = user.username,
                    profileImageURL = user.profileImageURL,
                    timestamp = System.currentTimeMillis(),
                    description = binding.descriptionED.text.toString(),
                    postImageURL = postImageURL ?: ""
                )
                viewModel.uploadPost(newPost)
            }
        }
    }

    private fun onCameraResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            viewModel.imageSelected.postValue(viewModel.tempCameraImage.value)
        }
    }

    private fun onGalleryResult(result: ActivityResult) {
        if (result.resultCode == Activity.RESULT_OK) {
            val uri = result.data?.data
            viewModel.imageSelected.postValue(uri)
        }
    }
    private fun onCameraPermissionResult(permissions: Map<String, Boolean>) {
        permissions.entries.forEach {
            if (!it.value) {
                Toast.makeText(requireContext(), "Permission ${it.key} denied", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun onGalleryPermissionResult(isGranted: Boolean) {
        if (!isGranted) {
            Toast.makeText(requireContext(), "Permission GALLERY denied", Toast.LENGTH_SHORT).show()
        }
    }


}