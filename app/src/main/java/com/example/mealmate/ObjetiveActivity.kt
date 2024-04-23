package com.example.mealmate.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mealmate.R
import com.example.mealmate.databinding.ActivityIngredientsFavBinding
import com.example.mealmate.databinding.ActivityObjetiveBinding

class ObjetiveActivity : AppCompatActivity() {
    private lateinit var binding: ActivityObjetiveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityObjetiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.InFavButton.setOnClickListener {
            
        }

    }
}