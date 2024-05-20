package com.example.mealmate.view.fragments

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealmate.databinding.FragmentRecipeDetailBinding
import com.example.mealmate.domain.model.Recipe
import com.example.mealmate.view.adapters.IngredientsAdapter
import com.example.mealmate.view.adapters.StepsAdapter
import com.example.mealmate.view.util.ImageUtil
import com.example.mealmate.viewmodel.RecipeDetailViewModel
import java.io.File
import java.util.UUID

class RecipeDetailFragment : Fragment() {

    private lateinit var binding: FragmentRecipeDetailBinding
    private val viewModel: RecipeDetailViewModel by viewModels()
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getString("id")?.let {
            viewModel.downloadRecipe(it)
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        }
        viewModel.recipeId.value = UUID.randomUUID().toString()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRecipeDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewModel.recipe.observe(viewLifecycleOwner) { recipe ->
            recipe?.let { displayRecipe(it) }
        }

        viewModel.imageSelected.observe(viewLifecycleOwner) { uri ->
            ImageUtil().renderImageCenterCrop(requireContext(), uri, binding.imageView)
        }

        binding.btnUpload.setOnClickListener {
            viewModel.uploadImage()
        }

        binding.btnCamera.setOnClickListener {
            if (isCameraPermissionsGranted()) {
                openCamera()
            } else {
                requestCameraPermissions()
            }
        }

        binding.btnGallery.setOnClickListener {
            if (isGalleryPermissionsGranted()) {
                openGallery()
            } else {
                requestGalleryPermissions()
            }
        }
    }

    private fun displayRecipe(recipe: Recipe) {
        binding.titleRecipe.text = recipe.title
        binding.weightRecipe.text = recipe.weight
        binding.kalRecipe.text = recipe.kal
        binding.portionRecipe.text = recipe.portion
        binding.timeRecipe.text = recipe.time
        binding.ingredientsRecipe.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = IngredientsAdapter(recipe.ingredients)
        }
        binding.stepsRecipe.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = StepsAdapter(recipe.steps)
        }
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        val file = File("${activity?.getExternalFilesDir(null)}/profile.png")
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

    private fun isCameraPermissionsGranted(): Boolean {
        val permissions = mutableListOf(
            Manifest.permission.CAMERA
        ).apply {
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }.toTypedArray()
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
            ).apply {
                if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
                    add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                }
            }.toTypedArray()
        )
    }

    private fun requestGalleryPermissions() {
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.P) {
            galleryPermissionLauncher.launch(Manifest.permission.READ_EXTERNAL_STORAGE)
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
