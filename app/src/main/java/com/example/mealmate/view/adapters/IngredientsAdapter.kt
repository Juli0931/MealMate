package com.example.mealmate.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mealmate.R
import com.example.mealmate.domain.model.Recipe

class IngredientsAdapter(private var ingredients: List<String>) : RecyclerView.Adapter<IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    override fun getItemCount(): Int = ingredients.size
    fun updateIngredientsList(ingredients: List<String>) {
        val diffUtil = IngredientsDiffUtil(this.ingredients, ingredients)
        val diffResults = DiffUtil.calculateDiff(diffUtil)
        this.ingredients = ingredients.toList()
        diffResults.dispatchUpdatesTo(this)
    }
}

class IngredientViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textViewIngredient: TextView = itemView.findViewById(R.id.textViewIngredient)

    fun bind(ingredient: String) {
        textViewIngredient.text = ingredient
    }

    companion object {
        fun create(parent: ViewGroup): IngredientViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_ingredient, parent, false)
            return IngredientViewHolder(view)
        }
    }
}

class IngredientsDiffUtil(
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