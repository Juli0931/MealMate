package com.example.mealmate.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealmate.R

class IngredientsAdapter(private val ingredients: List<String>) : RecyclerView.Adapter<IngredientViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IngredientViewHolder {
        return IngredientViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: IngredientViewHolder, position: Int) {
        holder.bind(ingredients[position])
    }

    override fun getItemCount(): Int = ingredients.size
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
