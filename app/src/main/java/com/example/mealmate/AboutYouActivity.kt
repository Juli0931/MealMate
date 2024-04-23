package com.example.mealmate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mealmate.databinding.ActivityAboutYouBinding

class AboutYouActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityAboutYouBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.btnContinuar.setOnClickListener {
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
        }

        binding.button2.setOnClickListener {
            startActivity(
                Intent(this, ProfileActivity::class.java)
            )
        }
    }
}

