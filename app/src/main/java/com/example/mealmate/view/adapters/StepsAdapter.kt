package com.example.mealmate.view.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealmate.R

class StepsAdapter(private val steps: List<String>) : RecyclerView.Adapter<StepViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepViewHolder {
        return StepViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: StepViewHolder, position: Int) {
        holder.bind(steps[position])
    }

    override fun getItemCount(): Int = steps.size
}

class StepViewHolder private constructor(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val textViewStep: TextView = itemView.findViewById(R.id.textViewStep)

    fun bind(step: String) {
        textViewStep.text = step
    }

    companion object {
        fun create(parent: ViewGroup): StepViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.item_step, parent, false)
            return StepViewHolder(view)
        }
    }
}
