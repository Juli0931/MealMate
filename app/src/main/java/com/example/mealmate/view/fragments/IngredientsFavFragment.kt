package com.example.mealmate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.mealmate.databinding.IngredientsFavFragmentBinding
import com.example.mealmate.view.util.ChipGroupUtil
import com.example.mealmate.viewmodel.PreferenceField
import com.example.mealmate.viewmodel.SurveyViewModel

class IngredientsFavFragment : Fragment() {

    private val viewModel: SurveyViewModel by activityViewModels()

    private lateinit var binding : IngredientsFavFragmentBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding =  IngredientsFavFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.currentPreferenceField.postValue(PreferenceField.INGREDIENTS)
        viewModel.getPreferencesByField(PreferenceField.VEGETABLES.name) { userPreferenceList ->
            ChipGroupUtil().generateChipGroupByPreferences(
                context = requireContext(),
                scope = lifecycleScope,
                userPreferenceList = userPreferenceList,
                selectedPreferenceList = viewModel.vegetables.value ?: emptyList(),
                layoutInflater = layoutInflater,
                chipGroup = binding.vegetablesChipGroup,
                onRemoveItem = { item -> viewModel.vegetables.value?.remove(item) },
                onAddItem = { item -> viewModel.vegetables.value?.add(item) }
            )
        }

        viewModel.getPreferencesByField(PreferenceField.CONDIMENTS.name) { userPreferenceList ->
            ChipGroupUtil().generateChipGroupByPreferences(
                context = requireContext(),
                scope = lifecycleScope,
                userPreferenceList = userPreferenceList,
                selectedPreferenceList = viewModel.condiments.value ?: emptyList(),
                layoutInflater = layoutInflater,
                chipGroup = binding.condimentsChipGroup,
                onRemoveItem = { item -> viewModel.condiments.value?.remove(item) },
                onAddItem = { item -> viewModel.condiments.value?.add(item) }
            )
        }

        viewModel.getPreferencesByField(PreferenceField.GRAINS.name) { userPreferenceList ->
            ChipGroupUtil().generateChipGroupByPreferences(
                context = requireContext(),
                scope = lifecycleScope,
                userPreferenceList = userPreferenceList,
                selectedPreferenceList = viewModel.grains.value ?: emptyList(),
                layoutInflater = layoutInflater,
                chipGroup = binding.grainsChipGroup,
                onRemoveItem = { item -> viewModel.grains.value?.remove(item) },
                onAddItem = { item -> viewModel.grains.value?.add(item) }
            )
        }

    }

    // Método estatico para generar instancias del propio fragment
    companion object{
        fun newInstance(): IngredientsFavFragment {
            return IngredientsFavFragment()
        }
    }

}
