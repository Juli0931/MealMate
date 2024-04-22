package com.example.mealmate.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mealmate.R
import com.example.mealmate.databinding.ActivityIngredientsFavBinding

class ExceptionsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIngredientsFavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientsFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.InFavButton.setOnClickListener {

        }
    }
}