package com.example.mealmate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealmate.databinding.FragmentHomeBinding
import com.example.mealmate.view.activities.NavigationListener
import com.example.mealmate.view.adapters.RecipesAdapter
import com.example.mealmate.viewmodel.HomeViewModel
import tech.benhack.ui.helpers.ImageUtil


class HomeFragment : Fragment(), RecipesAdapter.RenderImageListener {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var recipesAdapter: RecipesAdapter
    lateinit var navigationListener: NavigationListener
    private val imageUtil:ImageUtil by lazy { ImageUtil() }
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
        recipesAdapter.listener = this
        with(binding.recipesRecycler){
            adapter = recipesAdapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            addItemDecoration(DividerItemDecoration(requireContext(),
                GridLayoutManager.VERTICAL))
        }
        binding.titleTV.setOnClickListener{
            viewModel.init()
        }
        viewModel.recipeList.observe(viewLifecycleOwner){ recipeList ->
            recipesAdapter.updateRecipeList(recipeList)
        }

        binding.descriptionTV.setOnClickListener {
            val a = navigationListener
            val b = 45
            navigationListener.showFragment(RecipeDetailFragment())

        }
    }
    override fun render(url: String, image: ImageView) {
        imageUtil.renderImageCenterCrop(requireContext(),url,image)
    }
}