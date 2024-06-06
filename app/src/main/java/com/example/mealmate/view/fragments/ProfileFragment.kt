package com.example.mealmate.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mealmate.databinding.ProfileFragmentBinding
import com.example.mealmate.domain.model.CurrentSession
import org.checkerframework.checker.units.qual.Current

class ProfileFragment : Fragment() {

    private lateinit var binding: ProfileFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val currentUser = CurrentSession.currentUser
                binding.nameTV.text = currentUser.username
                binding.emailTV.text = currentUser.email
    }

    // Método estatico para generar instancias del propio fragment
    companion object{
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

}
