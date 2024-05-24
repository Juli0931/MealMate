package com.example.mealmate.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mealmate.R
import com.example.mealmate.databinding.ItemPostBinding
import com.example.mealmate.domain.model.RecipePost

class PostAdapter(private var recipePost: List<RecipePost>) : RecyclerView.Adapter<RecipePostViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        return StepViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.bind(steps[position])
    }

    override fun getItemCount(): Int = steps.size
    fun updateStepsList(steps: List<String>) {
        val diffUtil = IngredientsDiffUtil(this.steps, steps)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        this.steps = steps.toList()
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
        binding.postTime.text = recipePost.postTime.toString()
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
        fun create(parent: ViewGroup): RecipePostViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_post, parent, false)
            return RecipePostViewHolder(view)
        }
    }

    interface RecipePostListener{
        fun renderImage(url:String, image:ImageView)
        fun onClickLikeIcon(id:String, isSelected:Boolean)
        fun onClickShareIcon(id:String)
        fun onClickCommentIcon(id:String)
    }
}

class StepsDiffUtil(
    private val oldList: List<String>,
    private val newList: List<String>
): DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
