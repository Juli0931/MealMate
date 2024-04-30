package com.example.mealmate.presentation.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mealmate.R
import com.example.mealmate.databinding.ActivitySurveyBinding
import com.example.mealmate.presentation.fragments.AboutYouFragment
import com.example.mealmate.presentation.fragments.ExceptionsFragment
import com.example.mealmate.presentation.fragments.IngredientsFavFragment
import com.example.mealmate.presentation.fragments.ObjetiveFragment
import com.example.mealmate.presentation.fragments.OnboardingFragment

@Suppress("MoveVariableDeclarationIntoWhen")
class SurveyActivity : AppCompatActivity() {

    private val binding by lazy {
        ActivitySurveyBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)



        binding.continueButton.setOnClickListener {
            replaceFragmentForward()
        }

        binding.backButton.setOnClickListener {
            replaceFragmentBackward()
        }


    }

    private fun replaceFragmentForward() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerSurvey)
        when(currentFragment){
            is AboutYouFragment -> showFragment(OnboardingFragment())
            is OnboardingFragment -> showFragment(ExceptionsFragment())
            is ExceptionsFragment -> showFragment(ObjetiveFragment())
            is ObjetiveFragment -> showFragment(IngredientsFavFragment())
        }
    }

    private fun replaceFragmentBackward() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerSurvey)
        when(currentFragment){
            is ExceptionsFragment -> showFragment(OnboardingFragment())
            is ObjetiveFragment -> showFragment(ExceptionsFragment())
            is IngredientsFavFragment -> showFragment(ObjetiveFragment())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerSurvey, fragment)
            .commit()
    }
}