package com.example.mealmate

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.mealmate.activities.OnboardingActivity

/*class OnboardingActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: OnboardingAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val onboardingItems = listOf(
            OnboardingItem(listOf("Name1", "Name1", "Name1", "Name1", "Name1", "Name1", "Name1")),
        )

        recyclerView = findViewById(R.id.recyclerView)
        adapter = OnboardingAdapter(onboardingItems)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}
*/

class OnboardingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_onboarding)

        val continuarButton2: Button = findViewById(R.id.btn_continuar)

        continuarButton2.setOnClickListener {
            val intent = Intent(this, OnboardingActivity::class.java)
            startActivity(intent)
        }
    }
}
