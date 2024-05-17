package com.example.mealmate.view.fragments

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mealmate.databinding.FragmentRecipeDetailBinding
import com.example.mealmate.viewmodel.RecipeDetailViewModel
import java.io.File
import java.util.UUID

class RecipeDetailFragment : Fragment() {

    private lateinit var binding:FragmentRecipeDetailBinding
    private val viewModel:RecipeDetailViewModel by viewModels()
    val galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::onGalleryResult)
    val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ::onCameraResult)

    private fun onCameraResult(result: ActivityResult) {
        if(result.resultCode == Activity.RESULT_OK){
            viewModel.imageSelected.postValue(viewModel.tempCameraImage.value)
        }
    }
    private fun onGalleryResult(result: ActivityResult) {
        if(result.resultCode == Activity.RESULT_OK){
            val uri = result.data?.data
            viewModel.imageSelected.postValue(uri)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.recipeId.postValue(UUID.randomUUID().toString())


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

        viewModel.imageSelected.observe(viewLifecycleOwner){
            binding.imageView.setImageURI(it)
        }

        binding.btnUpload.setOnClickListener{
            viewModel.uploadImage()
        }

        binding.btnCamera.setOnClickListener{
            val i = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            val file = File("${activity?.getExternalFilesDir(null)}/profile.png")
            val uri = FileProvider.getUriForFile(requireContext(), requireActivity().packageName, file)
            i.putExtra(MediaStore.EXTRA_OUTPUT, uri)
            viewModel.tempCameraImage.postValue(uri)
            cameraLauncher.launch(i)
        }

        binding.btnGallery.setOnClickListener{
            val i = Intent(Intent.ACTION_GET_CONTENT)
            i.type = "image/*"
            galleryLauncher.launch(i)
        }
    }
}