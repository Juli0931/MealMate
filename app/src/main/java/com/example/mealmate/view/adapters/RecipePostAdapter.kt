package com.example.mealmate.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mealmate.R
import com.example.mealmate.databinding.ItemPostBinding
import com.example.mealmate.domain.model.RecipePost

class RecipePostAdapter(
    private var recipePost: List<RecipePost>,
    private val listener:RecipePostViewHolder.RecipePostListener
) : RecyclerView.Adapter<RecipePostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipePostViewHolder {
        return RecipePostViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: RecipePostViewHolder, position: Int) {
        holder.bind(recipePost[position])
    }

    override fun getItemCount(): Int = recipePost.size
    fun updateRecipePostList(recipePost: List<RecipePost>) {
        val diffUtil = RecipePostDiffUtil(this.recipePost, recipePost)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        this.recipePost = recipePost.toList()
        diffResults.dispatchUpdatesTo(this)
    }
}

//TODO: create format function to show postTime
class RecipePostViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemPostBinding.bind(itemView)
    lateinit var id:String
    lateinit var listener:RecipePostListener

    fun bind(recipePost: RecipePost) {
        id = recipePost.id
        binding.username.text = recipePost.username
        binding.postTime.text = recipePost.timestamp.toString()
        binding.postDescription.text = recipePost.description
        binding.postLikes.text = recipePost.totalLikes.toString()
        binding.postComments.text = recipePost.comments.toString()
        binding.postShares.text = recipePost.totalShares.toString()
        listener.renderImage(recipePost.profileImageURL, binding.profileImage)
        listener.renderImage(recipePost.postImageURL, binding.postImage)
    }

    //TODO: SAVE LIKES STATE
    init{
        binding.iconLike.setOnClickListener{listener.onClickLikeIcon(id, true)}
        binding.iconComment.setOnClickListener{listener.onClickCommentIcon(id)}
        binding.iconShare.setOnClickListener{listener.onClickShareIcon(id)}
    }
    companion object {
        fun create(parent: ViewGroup, listener:RecipePostListener): RecipePostViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
            val viewHolder = RecipePostViewHolder(view)
            viewHolder.listener = listener
            return viewHolder
        }
    }

    interface RecipePostListener{
        fun renderImage(url:String, image:ImageView)
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
