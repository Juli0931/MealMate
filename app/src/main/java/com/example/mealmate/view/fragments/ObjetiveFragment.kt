package com.example.mealmate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mealmate.databinding.ObjetiveFragmentBinding
import com.example.mealmate.viewmodel.PreferenceField
import com.example.mealmate.viewmodel.SurveyViewModel

class ObjetiveFragment : Fragment() {

    private val viewModel: SurveyViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: ObjetiveFragmentBinding = ObjetiveFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.currentPreferenceField.postValue(PreferenceField.OBJECTIVES)

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