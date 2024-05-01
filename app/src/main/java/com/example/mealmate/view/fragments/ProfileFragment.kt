package com.example.mealmate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mealmate.databinding.ProfileFragmentBinding

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ProfileFragmentBinding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Método estatico para generar instancias del propio fragment
    companion object{
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }
}
/*

    val binding by lazy {
        ActivityProfileBinding.inflate(layoutInflater)
    }

    val viewmodel: ProfileViewModel by viewModels()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        viewmodel.observeUser()

        viewmodel.userState.observe(this) {
            binding.emailTV.text = it.email
            binding.nameTV.text = it.name
        }


    }
*/