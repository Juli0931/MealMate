package com.example.mealmate.view.activities

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mealmate.R
import com.example.mealmate.databinding.ActivityAuthBinding
import com.example.mealmate.databinding.ActivityIngredientsSelectorBinding
import com.example.mealmate.view.adapters.IngredientViewHolder
import com.example.mealmate.view.adapters.IngredientsAdapter
import com.example.mealmate.viewmodel.IngredientsSelectorViewModel

class IngredientsSelectorActivity : AppCompatActivity(), IngredientViewHolder.HandleIngredientListener {

    private lateinit  var ingredientsAdapter: IngredientsAdapter
    private val viewModel:IngredientsSelectorViewModel by viewModels()

    private val binding by lazy {
        ActivityIngredientsSelectorBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setUpRecyclerView()
        displayIngredients()
    }

    private fun displayIngredients() {
        viewModel.ingredients.observe(this){
            ingredientsAdapter.updateIngredientsList(it)
        }
    }

    private fun setUpRecyclerView() {

        ingredientsAdapter = IngredientsAdapter(viewModel.ingredients.value ?: emptyList())
        ingredientsAdapter.listener = this
        binding.ingredientsRecyclerView.apply {
            layoutManager = LinearLayoutManager(this@IngredientsSelectorActivity, GridLayoutManager.VERTICAL, false)
            adapter = ingredientsAdapter
        }

    }

    override fun select(ingredient: String) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.putExtra("ingredient", ingredient)
        setResult(Activity.RESULT_OK, intent)
        finish()
    }
}
