package com.example.mealmate.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.mealmate.databinding.ObjetiveFragmentBinding

class ObjetiveFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ObjetiveFragmentBinding = ObjetiveFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    // Método estatico para generar instancias del propio fragment
    companion object{
        fun newInstance(): ObjetiveFragment {
            return ObjetiveFragment()
        }
    }

}
/*
    private lateinit var binding: ActivityObjetiveBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityObjetiveBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.InFavButton.setOnClickListener {
            
        }

    }
}

 */