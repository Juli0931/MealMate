package com.example.mealmate.view.navigation

import android.os.Bundle
import androidx.fragment.app.Fragment

interface NavigationListener {
    fun showFragment(fragment: Fragment):Boolean
}