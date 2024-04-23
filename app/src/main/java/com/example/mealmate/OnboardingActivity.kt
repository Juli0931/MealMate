package com.example.mealmate

import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.mealmate.databinding.ActivityAboutYouBinding
import com.example.mealmate.databinding.ActivityOnboardingBinding
import com.example.mealmate.viewmodel.SurveyState
import com.example.mealmate.viewmodel.SurveyViewModel

class OnboardingActivity : AppCompatActivity() {
    private val binding by lazy {
        ActivityOnboardingBinding.inflate(layoutInflater)
    }

    private val viewModel:SurveyViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val buttons = listOf(
            binding.btnLactoVegetariana,
            binding.btnOvoVegetariana,
            binding.btnOvoLactoVegetariana,
            binding.btnPescetariana,
            binding.btnVegana,
            binding.btnBajoCarbohidratos,
            binding.btnSinGluten
        )

        val dietNames = listOf(
            "Lacto Vegetariana",
            "Ovo Vegetariana",
            "Ovo Lacto Vegetariana",
            "Pescetariana",
            "Vegana",
            "Bajo Carbohidratos",
            "Sin Gluten"
        )

        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {

                val diet = dietNames[index]
                if (viewModel.diets.value!!.contains(diet)) {
                    viewModel.diets.value!!.remove(diet)
                } else {
                    viewModel.diets.value!!.add(diet)
                }

                if (viewModel.diets.value!!.contains(diet)) {
                    button.backgroundTintList = ColorStateList.valueOf(0x11151E)
                    button.setTextColor(0xD6FC51)
                } else {
                    button.backgroundTintList = ColorStateList.valueOf(0xD6FC51)
                    button.setTextColor(0x11151E)
                }
            }
        }

        viewModel.surveyState.observe(this) { state ->
            when (state!!) {
                SurveyState.SUCCESS -> {
                    val intent = Intent(this, ProfileActivity::class.java)
                    startActivity(intent)
                }
                SurveyState.ERROR -> {
                    Toast.makeText(this, "Error al guardar los datos", Toast.LENGTH_SHORT).show()
                    viewModel.surveyState.postValue(SurveyState.WAITING)
                }
                SurveyState.LOADING -> {
                    Toast.makeText(this, "Loading...", Toast.LENGTH_SHORT).show()
                }
                SurveyState.WAITING -> { }
            }
        }

        binding.btnContinuar.setOnClickListener {
            viewModel.uploadSurvey()
        }
    }
}
