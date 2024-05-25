package com.example.mealmate.view.navigation

import androidx.fragment.app.Fragment

interface NavigationListener {
    fun showFragment(fragment: Fragment):Boolean
}