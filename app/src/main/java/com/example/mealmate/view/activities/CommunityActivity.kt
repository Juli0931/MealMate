package com.example.mealmate.view.activities

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.mealmate.R
import com.example.mealmate.R.id.newPostBotton
import com.example.mealmate.view.fragments.CommunityFragment
import com.example.mealmate.view.fragments.NewPostFragment

class CommunityActivity: AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        if(savedInstanceState == null){
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerHome, CommunityFragment())
                .commit()
        }

        val imageView: ImageView = findViewById(newPostBotton)
        imageView.setOnClickListener{
            replaceFragment(NewPostFragment())
        }
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerHome, fragment)
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()
    }
}