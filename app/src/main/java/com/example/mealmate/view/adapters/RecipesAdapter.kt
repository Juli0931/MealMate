package com.example.mealmate.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.Adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.mealmate.R
import com.example.mealmate.databinding.RecipeItemBinding
import com.example.mealmate.domain.model.Recipe
import com.example.mealmate.domain.model.toGramsFormat
import com.example.mealmate.domain.model.toKalFormat

class RecipesAdapter(
   private var recipeList:List<Recipe>
):Adapter<RecipeViewHolder>() {

    fun updateRecipeList(newList:List<Recipe>){
        val diffUtil = RecipesDiffUtil(recipeList, newList)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        recipeList = newList
        diffResults.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecipeViewHolder {
       val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.recipe_item, parent, false)
        return RecipeViewHolder(view)
    }

    override fun getItemCount(): Int = recipeList.size

    override fun onBindViewHolder(holder: RecipeViewHolder, position: Int) =
        holder.render(recipeList[position])
}


class RecipeViewHolder(root:View):ViewHolder(root){
    private val binding = RecipeItemBinding.bind(root)

    fun render(recipe:Recipe){
        binding.titleTV.text = recipe.title
        binding.descriptionTV.text = recipe.description
        binding.weightTV.text = recipe.weight.toGramsFormat()
        binding.kalTV.text = recipe.weight.toKalFormat()
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