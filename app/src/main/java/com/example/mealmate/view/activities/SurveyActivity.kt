package com.example.mealmate.view.activities

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mealmate.R
import com.example.mealmate.databinding.ActivitySurveyBinding
import com.example.mealmate.view.fragments.AboutYouFragment
import com.example.mealmate.view.fragments.DietsFragment
import com.example.mealmate.view.fragments.ExceptionsFragment
import com.example.mealmate.view.fragments.IngredientsFavFragment
import com.example.mealmate.view.fragments.ObjetiveFragment
import com.example.mealmate.viewmodel.SurveyViewModel

@Suppress("MoveVariableDeclarationIntoWhen")
class SurveyActivity : AppCompatActivity() {

    private val viewModel: SurveyViewModel by viewModels()

    private var progress_bar:Int = 0


    private val binding by lazy {
        ActivitySurveyBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        progress_bar = binding.progressBar.max/4


        viewModel.currentPreferenceField.observe(this){
            hideButtons()
        }


        binding.continueButton.setOnClickListener {
            replaceFragmentForward()
            increaseProgressBar()

        }

        binding.backButton.setOnClickListener {
            replaceFragmentBackward()
            decreaseProgressBar()

        }


    }
    private fun increaseProgressBar(){
        binding.progressBar.incrementProgressBy(progress_bar)
    }

    private fun decreaseProgressBar(){
        binding.progressBar.incrementProgressBy(-progress_bar)
    }


    private fun hideButtons() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerSurvey)
        when (currentFragment) {
            is AboutYouFragment -> {
                binding.backButton.visibility = View.GONE
                binding.skipButton.visibility = View.GONE
                binding.progressBar.visibility = View.GONE
            }

            is DietsFragment -> {
                binding.backButton.visibility = View.GONE
                binding.skipButton.visibility = View.VISIBLE
                binding.progressBar.visibility = View.VISIBLE
            }

            is ExceptionsFragment -> {
                binding.backButton.visibility = View.VISIBLE
                binding.skipButton.visibility = View.VISIBLE
                binding.progressBar.visibility = View.VISIBLE
            }
            is ObjetiveFragment -> {
                binding.backButton.visibility = View.VISIBLE
                binding.skipButton.visibility = View.VISIBLE
                binding.progressBar.visibility = View.VISIBLE
            }

            is IngredientsFavFragment -> {
                binding.backButton.visibility = View.VISIBLE
                binding.skipButton.visibility = View.GONE
                binding.progressBar.visibility = View.VISIBLE
            }
        }
    }

    private fun replaceFragmentForward() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerSurvey)
        when (currentFragment) {
            is AboutYouFragment -> showFragment(DietsFragment())
            is DietsFragment -> showFragment(ExceptionsFragment())
            is ExceptionsFragment -> showFragment(ObjetiveFragment())
            is ObjetiveFragment -> showFragment(IngredientsFavFragment())
        }
    }

    private fun replaceFragmentBackward() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerSurvey)
        when (currentFragment) {
            is ExceptionsFragment -> showFragment(DietsFragment())
            is ObjetiveFragment -> showFragment(ExceptionsFragment())
            is IngredientsFavFragment -> showFragment(ObjetiveFragment())
        }
    }

    private fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerSurvey, fragment)
            .commit()
    }


}