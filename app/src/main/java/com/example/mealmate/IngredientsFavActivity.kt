package com.example.mealmate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import com.example.mealmate.R
import com.example.mealmate.databinding.ActivityIngredientsFavBinding

class IngredientsFavActivity : AppCompatActivity() {
    private lateinit var binding: ActivityIngredientsFavBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIngredientsFavBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.InFavButton.setOnClickListener {

        }
    }

}