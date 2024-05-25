package com.example.mealmate.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.mealmate.R
import com.example.mealmate.databinding.FragmentNewPostBinding
import com.example.mealmate.viewmodel.NewPostViewModel

class NewPostFragment : Fragment() {

    private lateinit var binding:FragmentNewPostBinding
    private val viewModel:NewPostViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewPostBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupButtons()

    }

    private fun setupButtons() {
        binding.closePost.setOnClickListener{
            activity?.onBackPressed()
        }
    }


}