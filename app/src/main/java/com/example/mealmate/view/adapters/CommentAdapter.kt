package com.example.mealmate.view.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mealmate.R
import com.example.mealmate.databinding.ItemCommentBinding
import com.example.mealmate.domain.model.Comment

class CommentAdapter(
    private var comments: List<Comment>,
    private val listener: CommentViewHolder.CommentListener
) : RecyclerView.Adapter<CommentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        return CommentViewHolder.create(parent, listener)
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bind(comments[position])
    }

    override fun getItemCount(): Int = comments.size
    fun updateCommentsList(comments: List<Comment>) {
        val diffUtil = CommentDiffUtil(this.comments, comments)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        this.comments = comments.toList()
        diffResults.dispatchUpdatesTo(this)
    }
}

//TODO: create format function to show postTime
@SuppressLint("ResourceAsColor")
class CommentViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val binding = ItemCommentBinding.bind(itemView)
    lateinit var id:String
    lateinit var listener:CommentListener
    @SuppressLint("ResourceAsColor")
    fun bind(comment: Comment) {
        id = comment.id
        binding.username.text = comment.username
        binding.postTime.text = comment.timestamp.toString()
        binding.textComment.text = comment.comment
        listener.renderImage(comment.profileImageURL, binding.profileImage)

    }
    companion object {
        fun create(parent: ViewGroup, listener:CommentListener): CommentViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_comment, parent, false)
            val viewHolder = CommentViewHolder(view)
            viewHolder.listener = listener
            return viewHolder
        }
    }
    interface CommentListener{
        fun renderImage(url:String, image:ImageView)
    }
}

class CommentDiffUtil(
    private val oldList: List<Comment>,
    private val newList: List<Comment>
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
