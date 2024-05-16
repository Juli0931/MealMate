package com.example.mealmate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.mealmate.R
import com.example.mealmate.databinding.FragmentRecipeDetailBinding
import com.example.mealmate.viewmodel.RecipeDetailViewModel
import java.util.UUID

class RecipeDetailFragment : Fragment() {

    private lateinit var binding:FragmentRecipeDetailBinding
    private val viewModel:RecipeDetailViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.recipeId.postValue(UUID.randomUUID().toString())
        viewModel.imageSelected.postValue(
            AppCompatResources.getDrawable(requireContext(), R.drawable.dragon)
        )
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

        binding.root.setOnClickListener{
            viewModel.uploadImage()
        }

    }
}