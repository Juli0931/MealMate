package com.example.mealmate.view.adapters

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mealmate.R
import com.example.mealmate.databinding.ItemPostBinding
import com.example.mealmate.domain.model.RecipePost
import com.example.mealmate.view.util.hasUserLikedIt

class RecipePostAdapter(
    private var recipePosts: List<RecipePost>,
    private val listener:RecipePostViewHolder.RecipePostListener,
    private val currentUserId:String
) : RecyclerView.Adapter<RecipePostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipePostViewHolder {
        return RecipePostViewHolder.create(parent, listener, currentUserId)
    }

    override fun onBindViewHolder(holder: RecipePostViewHolder, position: Int) {
        holder.bind(recipePosts[position])
    }

    override fun getItemCount(): Int = recipePosts.size
    fun updateRecipePostList(recipePost: List<RecipePost>) {
        val diffUtil = RecipePostDiffUtil(this.recipePosts, recipePost)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        this.recipePosts = recipePost.toList()
        diffResults.dispatchUpdatesTo(this)
    }
}

//TODO: create format function to show postTime
@SuppressLint("ResourceAsColor")
class RecipePostViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemPostBinding.bind(itemView)
    lateinit var id:String
    lateinit var listener:RecipePostListener
    lateinit var currentUserId: String
    private var hasLike: Boolean = false

    //TODO: change image to icon for likes, comments and shares
    @SuppressLint("ResourceAsColor")
    fun bind(recipePost: RecipePost) {
        hasLike = recipePost.likes.hasUserLikedIt(currentUserId)
        id = recipePost.id
        binding.username.text = recipePost.username
        binding.postTime.text = recipePost.timestamp.toString()
        binding.postDescription.text = recipePost.description
        binding.postLikes.text = recipePost.totalLikes.toString()
        binding.postComments.text = recipePost.comments.toString()
        binding.postShares.text = recipePost.totalShares.toString()

        listener.renderImage(recipePost.profileImageURL, binding.profileImage)
        listener.renderImage(recipePost.postImageURL, binding.postImage)
        listener.paintIconLike(hasLike, binding.iconLike)

    }

    //TODO: SAVE LIKES STATE
    init{
        binding.iconLike.setOnClickListener{
            hasLike = !hasLike
            listener.paintIconLike(hasLike, binding.iconLike)
            listener.onClickLikeIcon(id, hasLike)
        }
        binding.iconComment.setOnClickListener{listener.onClickCommentIcon(id)}
        binding.iconShare.setOnClickListener{listener.onClickShareIcon(id)}
    }
    companion object {
        fun create(parent: ViewGroup, listener:RecipePostListener, userId:String): RecipePostViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
            val viewHolder = RecipePostViewHolder(view)
            viewHolder.listener = listener
            viewHolder.currentUserId = userId
            return viewHolder
        }
    }

    interface RecipePostListener{
        fun renderImage(url:String, image:ImageView)
        fun paintIconLike(hasLike:Boolean, image:ImageView)
        fun onClickLikeIcon(id:String, isSelected:Boolean)
        fun onClickShareIcon(id:String)
        fun onClickCommentIcon(id:String)
    }
}

class RecipePostDiffUtil(
    private val oldList: List<RecipePost>,
    private val newList: List<RecipePost>
): DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
