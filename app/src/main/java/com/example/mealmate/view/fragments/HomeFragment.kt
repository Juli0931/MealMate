package com.example.mealmate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealmate.databinding.FragmentHomeBinding
import com.example.mealmate.view.adapters.RecipesAdapter
import com.example.mealmate.view.navigation.NavigationListener
import com.example.mealmate.view.util.ImageUtil
import com.example.mealmate.viewmodel.HomeViewModel


class HomeFragment(
    private val navigationListener: NavigationListener
) : Fragment(), RecipesAdapter.RenderImageListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recipesAdapter: RecipesAdapter
    private val imageUtil: ImageUtil by lazy { ImageUtil() }
    private val viewModel:HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.refresh()
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
        recipesAdapter.imageListener = this
        recipesAdapter.navigationListener = navigationListener
        with(binding.recipesRecycler){
            adapter = recipesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(),
                GridLayoutManager.VERTICAL))
        }
        binding.titleTV.setOnClickListener{
            viewModel.refresh()
        }
        viewModel.recipeList.observe(viewLifecycleOwner){ recipeList ->
            recipesAdapter.updateRecipeList(recipeList)
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.refresh()
        }
        viewModel.isLoading.observe(viewLifecycleOwner){isLoading ->
            binding.swipeRefresh.isRefreshing = isLoading
        }
    }
    override fun render(url: String, image: ImageView) {
        imageUtil.renderImageCenterCrop(requireContext(),url,image)
    }
}