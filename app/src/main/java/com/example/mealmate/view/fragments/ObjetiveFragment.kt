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
import com.google.android.material.chip.Chip

class ObjetiveFragment : Fragment() {

    private val viewModel: SurveyViewModel by activityViewModels()

    private lateinit var binding : ObjetiveFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         binding = ObjetiveFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.currentPreferenceField.postValue(PreferenceField.OBJECTIVES)
        viewModel.getPreferencesByField(PreferenceField.OBJECTIVES.name) { chipList ->

            chipList.forEach { chipData ->

                val chip = Chip(requireContext())
                chip.text = chipData.name
                chip.setOnClickListener {
                    // Handle chip click
                }
                binding.objectiveChipGroup.addView(chip)
            }
        }

    }

    // Método estatico para generar instancias del propio fragment
    companion object{
        fun newInstance(): ObjetiveFragment {
            return ObjetiveFragment()
        }
    }

}
