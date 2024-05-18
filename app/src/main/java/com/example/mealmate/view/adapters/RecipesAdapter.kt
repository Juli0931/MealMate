package com.example.mealmate.view.adapters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mealmate.R
import com.example.mealmate.databinding.RecipeItemBinding
import com.example.mealmate.domain.model.Recipe
import com.example.mealmate.domain.model.toGramsFormat
import com.example.mealmate.domain.model.toKalFormat
import com.example.mealmate.view.activities.NavigationListener
import com.example.mealmate.view.fragments.RecipeDetailFragment

class RecipesAdapter(
   private var recipeList:List<Recipe>
):Adapter<RecipeViewHolder>() {

    lateinit var imageListener:RenderImageListener
    lateinit var navigationListener:NavigationListener

    fun updateRecipeList(newList:List<Recipe>){
        val diffUtil = RecipesDiffUtil(recipeList, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        recipeList = newList.toList()
        diffResults.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun getItemCount(): Int = recipeList.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int){
        holder.navigationListener = navigationListener
        holder.render(recipeList[position])
        imageListener.render(recipeList[position].img, holder.imageView)
    }

    interface RenderImageListener{
        fun render(url:String,image:ImageView)
    }
}


class RecipeViewHolder(root:View):ViewHolder(root){
    lateinit var navigationListener: NavigationListener
    private val binding = RecipeItemBinding.bind(root)
    val imageView = binding.img
    var recipeID = ""
    fun render(recipe:Recipe){
        recipeID = recipe.id
        binding.titleTV.text = recipe.title
        binding.descriptionTV.text = recipe.description
        binding.weightTV.text = recipe.weight.toGramsFormat()
        binding.kalTV.text = recipe.weight.toKalFormat()
    }
    init {
        binding.root.setOnClickListener{
            val fragment = RecipeDetailFragment()
            val args = Bundle()
            args.putString("id", recipeID)
            fragment.arguments = args
            navigationListener.showFragment(fragment)
        }
    }
}

class RecipesDiffUtil(
    private val oldList: List<Recipe>,
    private val newList: List<Recipe>
):DiffUtil.Callback(){
    override fun getOldListSize(): Int = oldList.size

    override fun getNewListSize(): Int = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
         return oldList[oldItemPosition].id == newList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}