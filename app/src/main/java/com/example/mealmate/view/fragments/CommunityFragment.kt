package com.example.mealmate.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealmate.R
import com.example.mealmate.databinding.FragmentCommunityBinding
import com.example.mealmate.domain.model.CurrentSession
import com.example.mealmate.view.adapters.RecipePostAdapter
import com.example.mealmate.view.adapters.RecipePostViewHolder
import com.example.mealmate.view.navigation.NavigationListener
import com.example.mealmate.view.util.ImageUtil
import com.example.mealmate.viewmodel.CommunityViewModel
import org.checkerframework.checker.units.qual.Current

class CommunityFragment(
    private val navigationListener: NavigationListener
) : Fragment(), RecipePostViewHolder.RecipePostListener  {

    private lateinit var binding:FragmentCommunityBinding
    private lateinit var adapter:RecipePostAdapter
    private val viewModel:CommunityViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommunityBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupButtons()
        observeStates()
        viewModel.refresh()
    }

    //TODO: Resolve swipe refresh bug when user navigates between fragments
    private fun setupButtons() {
        binding.newPostBotton.setOnClickListener{
            val fragment = NewPostFragment()
            navigationListener.showFragment(fragment)
        }
        binding.swipeRefresh.setOnRefreshListener {
          //  viewModel.refresh()
            binding.swipeRefresh.isRefreshing = false
        }

    }

    //TODO: Resolve swipe refresh bug when user navigates between fragments
    private fun observeStates() {
        viewModel.recipePostList.observe(viewLifecycleOwner){
            adapter.updateRecipePostList(it ?: emptyList())
        }
        viewModel.isLoading.observe(viewLifecycleOwner){isLoading ->
         //   binding.swipeRefresh.isRefreshing = isLoading
        }
    }

    private fun setupRecyclerView() {
        adapter = RecipePostAdapter(
            viewModel.recipePostList.value ?: emptyList(),
            this,
            CurrentSession.currentUser.id
            )
        with(binding.postRecyclerview){
            adapter = this@CommunityFragment.adapter
            layoutManager =
                LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        }
    }

    override fun renderImage(url: String, image: ImageView) {
        ImageUtil().renderImageCenterCrop(requireContext(), url, image)
    }

    override fun paintIconLike(hasLike: Boolean, image: ImageView) {
        if(hasLike){
            image.background = ContextCompat.getDrawable(requireContext(),R.color.red)

        }else{
            image.background = ContextCompat.getDrawable(requireContext(),R.color.black)
        }
    }

    override fun onClickLikeIcon(id: String, isSelected: Boolean) {
        viewModel.updateLike(id, isSelected)
    }

    override fun onClickShareIcon(id: String) {
        viewModel.sharePost(id)
    }

    override fun onClickCommentIcon(id: String) {
        val bundle = Bundle().apply {
            putString("recipePostId", id)
        }
        CommentFragment().run {
            arguments = bundle
            navigationListener.showFragment(this)
        }

    }

}