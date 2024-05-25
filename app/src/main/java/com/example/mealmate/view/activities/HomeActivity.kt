package com.example.mealmate.view.activities

import android.app.Activity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.mealmate.R
import com.example.mealmate.databinding.ActivityHomeBinding
import com.example.mealmate.view.fragments.CommunityFragment
import com.example.mealmate.view.fragments.HomeFragment
import com.example.mealmate.view.fragments.MealPlannerFragment
import com.example.mealmate.view.fragments.NewPostFragment
import com.example.mealmate.view.fragments.RecipeDetailFragment
import com.example.mealmate.view.navigation.NavigationListener

class HomeActivity : AppCompatActivity(), NavigationListener {

    private lateinit var homeFragment: HomeFragment
    private lateinit var communityFragment: CommunityFragment
    private lateinit var plannerFragment: MealPlannerFragment

    private val binding by lazy {
        ActivityHomeBinding.inflate(layoutInflater)
    }

    override fun onCreate (savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        homeFragment = HomeFragment(this)
        communityFragment = CommunityFragment(this)
        plannerFragment = MealPlannerFragment()

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.action_home ->  showFragment(homeFragment)
                R.id.action_community ->  showFragment(communityFragment)
                R.id.action_planner ->  showFragment(plannerFragment)
                else -> false
            }
        }
        showFragment(homeFragment)
    }

     override fun showFragment(fragment: Fragment) : Boolean {
        supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerHome, fragment)
            .commit()
        supportFragmentManager.executePendingTransactions()
        showNavigationBar()
        return true
    }

    private fun showNavigationBar(){
        val currentFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerHome)
        when(currentFragment){
            is HomeFragment -> binding.bottomNavigation.visibility = View.VISIBLE
            is CommunityFragment -> binding.bottomNavigation.visibility = View.VISIBLE
            is MealPlannerFragment -> binding.bottomNavigation.visibility = View.VISIBLE
            else -> binding.bottomNavigation.visibility = View.GONE
        }
    }
    @Deprecated("Deprecated in Java")
    override fun onBackPressed() {
        val currentFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainerHome)
        when(currentFragment){
            is RecipeDetailFragment -> showFragment(homeFragment)
            is NewPostFragment -> showFragment(communityFragment)
            else -> super.onBackPressed()
        }
    }
}


