package com.example.mealmate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.mealmate.databinding.DietsFragmentBinding
import com.example.mealmate.view.util.ChipGroupUtil
import com.example.mealmate.viewmodel.PreferenceField
import com.example.mealmate.viewmodel.SurveyViewModel
import com.google.android.material.chip.Chip

class DietsFragment : Fragment() {

    private val viewModel: SurveyViewModel by activityViewModels()

    private lateinit var binding: DietsFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DietsFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.currentPreferenceField.postValue(PreferenceField.DIETS)

        viewModel.getPreferencesByField(PreferenceField.DIETS.name) { userPreferenceList ->
            ChipGroupUtil().generateChipGroupByPreferences(
                context = requireContext(),
                scope = lifecycleScope,
                userPreferenceList = userPreferenceList,
                selectedPreferenceList = viewModel.diets.value ?: emptyList(),
                layoutInflater = layoutInflater,
                chipGroup = binding.dietChipGroup,
                onRemoveItem = { item -> viewModel.diets.value?.remove(item) },
                onAddItem = { item -> viewModel.diets.value?.add(item) }
            )
        }

    }



    companion object {
        fun newInstance(): DietsFragment {
            return DietsFragment()
        }
    }
}