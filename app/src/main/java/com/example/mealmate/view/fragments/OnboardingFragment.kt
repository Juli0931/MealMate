package com.example.mealmate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mealmate.databinding.OnboardingFragmentBinding

class OnboardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: OnboardingFragmentBinding = OnboardingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Método estatico para generar instancias del propio fragment
    companion object{
        fun newInstance(): OnboardingFragment {
            return OnboardingFragment()
        }
    }





}
/*

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
                    val intent = Intent(this, ProfileFragment::class.java)
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

*/