package com.example.mealmate.view.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.example.mealmate.viewmodel.PreferenceField
import com.example.mealmate.view.state.UIState
import com.example.mealmate.viewmodel.SurveyViewModel
import com.google.firebase.ktx.Firebase
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings

@Suppress("MoveVariableDeclarationIntoWhen")
class SurveyActivity : AppCompatActivity() {

    private val viewModel: SurveyViewModel by viewModels()

    private var progressBar: Int = 0


    private val binding by lazy {
        ActivitySurveyBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        initRemoteConfig()
        viewModel.init()
        progressBar = (binding.progressBar.max) / 4

        viewModel.currentPreferenceField.observe(this) {
            hideButtons()
        }
        binding.continueButton.setOnClickListener {
            if(viewModel.currentPreferenceField.value == PreferenceField.ABOUT_YOU){
                replaceFragmentForward()
                increaseProgressBar()
            }else{
                viewModel.uploadUserPreference()
            }
        }
        binding.backButton.setOnClickListener {
            replaceFragmentBackward()
            decreaseProgressBar()
        }

        viewModel.uiState.observe(this){ state ->
            when(state){
                UIState.WAITING -> {}
                UIState.LOADING -> {
                    Toast.makeText(this, "Loading...", Toast.LENGTH_LONG).show()
                }
                UIState.SUCCESS -> {
                    Toast.makeText(this, "Preferencias actualizadas exitosamente", Toast.LENGTH_LONG).show()
                    viewModel.uiState.postValue(UIState.WAITING)
                    replaceFragmentForward()
                    increaseProgressBar()
                }
                UIState.ERROR -> {
                    Toast.makeText(this, "Error...", Toast.LENGTH_LONG).show()
                    viewModel.uiState.postValue(UIState.WAITING)
                }
            }

        }

    }

    private fun initRemoteConfig() {

        val remoteConfig: FirebaseRemoteConfig = Firebase.remoteConfig
        val configSettings = remoteConfigSettings {
            minimumFetchIntervalInSeconds = 30
        }
        remoteConfig.setConfigSettingsAsync(configSettings)
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)

    }

    private fun increaseProgressBar() {
        binding.progressBar.incrementProgressBy(progressBar)
    }

    private fun decreaseProgressBar() {
        binding.progressBar.incrementProgressBy(-progressBar)
    }

    private fun replaceFragmentForward() {
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerSurvey)
        when (currentFragment) {
            is AboutYouFragment -> showFragment(DietsFragment())
            is DietsFragment -> showFragment(ExceptionsFragment())
            is ExceptionsFragment -> showFragment(ObjetiveFragment())
            is ObjetiveFragment -> showFragment(IngredientsFavFragment())
            is IngredientsFavFragment -> {
                startActivity(Intent(this, HomeActivity::class.java))
                finish()
            }
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
                binding.omitButton.visibility = View.GONE
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


}