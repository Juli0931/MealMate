package com.example.mealmate.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealmate.R
import com.example.mealmate.databinding.FragmentHomeBinding
import com.example.mealmate.databinding.LoginFragmentBinding
import com.example.mealmate.domain.model.Recipe
import com.example.mealmate.view.adapters.RecipesAdapter
import com.example.mealmate.viewmodel.HomeViewModel


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recipesAdapter: RecipesAdapter
    private val viewModel:HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init()
    }

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recipesAdapter = RecipesAdapter(viewModel.recipeList.value ?: emptyList())
        with(binding.recipesRecycler){
            adapter = recipesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(),
                GridLayoutManager.VERTICAL))
        }

        viewModel.recipeList.observe(viewLifecycleOwner){ recipeList ->

            recipesAdapter.recipeList = recipeList
            recipesAdapter.notifyDataSetChanged()

        }
    }
}