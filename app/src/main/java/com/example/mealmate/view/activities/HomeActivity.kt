package com.example.mealmate.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.mealmate.R
import com.example.mealmate.databinding.ActivityAuthBinding
import com.example.mealmate.databinding.ActivityHomeBinding
import com.example.mealmate.view.fragments.CommunityFragment
import com.example.mealmate.view.fragments.HomeFragment
import com.example.mealmate.view.fragments.MealPlannerFragment

class HomeActivity : AppCompatActivity() {

    private lateinit var homeFragment: HomeFragment
    private lateinit var communityFragment: CommunityFragment
    private lateinit var plannerFragment: MealPlannerFragment

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        homeFragment = HomeFragment()
        communityFragment = CommunityFragment()
        plannerFragment = MealPlannerFragment()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.action_home ->  showFragment(homeFragment)
                R.id.action_community ->  showFragment(communityFragment)
                R.id.action_planner ->  showFragment(plannerFragment)
                else -> false
            }
        }
    }

    private fun showFragment(fragment: Fragment) : Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerHome, fragment)
            .commit()
        return true
    }


}