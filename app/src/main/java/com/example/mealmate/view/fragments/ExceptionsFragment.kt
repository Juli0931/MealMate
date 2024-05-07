package com.example.mealmate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mealmate.databinding.ExceptionsFragmentBinding
import com.example.mealmate.viewmodel.PreferenceField
import com.example.mealmate.viewmodel.SurveyViewModel
import com.google.android.material.chip.Chip

class ExceptionsFragment : Fragment() {

    private val viewModel: SurveyViewModel by activityViewModels()

    private lateinit var binding: ExceptionsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ExceptionsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.currentPreferenceField.postValue(PreferenceField.EXCEPTIONS)

        viewModel.getPreferencesByField(PreferenceField.EXCEPTIONS.name) { chipList ->

            chipList.forEach { chipData ->

                val chip = Chip(requireContext())
                chip.text = chipData.name
                chip.setOnClickListener {
                    // Handle chip click
                }
                binding.exceptionsChipGroup.addView(chip)
            }
        }

    }

    // Método estatico para generar instancias del propio fragment
    companion object{
        fun newInstance(): ExceptionsFragment {
            return ExceptionsFragment()
        }
    }
}
