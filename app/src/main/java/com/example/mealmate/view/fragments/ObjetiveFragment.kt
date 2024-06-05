package com.example.mealmate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.mealmate.databinding.ObjetiveFragmentBinding
import com.example.mealmate.view.util.ChipGroupUtil
import com.example.mealmate.viewmodel.PreferenceField
import com.example.mealmate.viewmodel.SurveyViewModel

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
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentPreferenceField.postValue(PreferenceField.OBJECTIVES)
        viewModel.getPreferencesByField(PreferenceField.OBJECTIVES.name) { userPreferenceList ->
            ChipGroupUtil().generateChipGroupByPreferences(
                context = requireContext(),
                scope = lifecycleScope,
                userPreferenceList = userPreferenceList,
                selectedPreferenceList = viewModel.objectives.value ?: emptyList(),
                layoutInflater = layoutInflater,
                chipGroup = binding.objectiveChipGroup,
                onRemoveItem = { item -> viewModel.objectives.value?.remove(item) },
                onAddItem = { item -> viewModel.objectives.value?.add(item) }
            )
        }

    }

    // Método estatico para generar instancias del propio fragment
    companion object{
        fun newInstance(): ObjetiveFragment {
            return ObjetiveFragment()
        }
    }

}
