package com.example.mealmate.view.fragments

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealmate.databinding.FragmentCommentBinding
import com.example.mealmate.domain.model.Comment
import com.example.mealmate.domain.model.CurrentSession
import com.example.mealmate.view.adapters.CommentAdapter
import com.example.mealmate.view.adapters.CommentViewHolder
import com.example.mealmate.view.util.ImageUtil
import com.example.mealmate.viewmodel.CommentViewModel
import java.util.UUID


class CommentFragment : Fragment(), CommentViewHolder.CommentListener {
    private lateinit var binding: FragmentCommentBinding
    private val viewModel: CommentViewModel by viewModels()
    lateinit var adapter: CommentAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val postID = arguments?.getString("recipePostId")
        if(postID != null){
            viewModel.postID = postID
        }else{
            activity?.onBackPressed()
            Toast.makeText(requireContext(),
                "Error al entrar en comentarios",
                Toast.LENGTH_LONG)
                .show()
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCommentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerview()
        observeStates()
        setupViewsListeners()
        binding.commentET.requestFocus()
        showKeyboard(binding.commentET)
        viewModel.refresh()
    }

    private fun setupRecyclerview(){
        adapter = CommentAdapter(viewModel.comments.value ?: emptyList(), this)
        with(binding.commentRecyclerview){
            adapter = this@CommentFragment.adapter
            layoutManager =
                LinearLayoutManager(requireContext(), androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
        }
    }

    private fun observeStates() {
        viewModel.comments.observe(viewLifecycleOwner){
            adapter.updateCommentsList(it)
        }

    }
    private fun setupViewsListeners(){
        binding.commentET.setOnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                val rootView = activity?.window?.decorView?.findViewById<View>(android.R.id.content)
                rootView?.viewTreeObserver?.addOnGlobalLayoutListener {
                    val rect = Rect()
                    rootView.getWindowVisibleDisplayFrame(rect)
                    val screenHeight = rootView.height
                    val keypadHeight = screenHeight - rect.bottom
                    if (keypadHeight > screenHeight * 0.15) {
                        binding.commentET.translationY = -keypadHeight.toFloat()
                    } else {
                        binding.commentET.translationY = 0f
                    }
                }
            }
        }

        binding.btnPostComment.setOnClickListener {
            val comment = Comment(
                id = UUID.randomUUID().toString(),
                username = CurrentSession.currentUser.username,
                profileImageURL = CurrentSession.currentUser.profileImageURL,
                timestamp = System.currentTimeMillis(),
                comment = binding.commentET.text.toString()
            )
            viewModel.uploadComment(comment)
            binding.commentET.setText("")
        }
    }
    private fun showKeyboard(editText: EditText) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun renderImage(url: String, image: ImageView) {
        ImageUtil().renderImageCenterCrop(requireContext(), url, image)
    }

}