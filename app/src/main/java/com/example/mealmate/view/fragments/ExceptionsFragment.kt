package com.example.mealmate.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.mealmate.databinding.ExceptionsFragmentBinding
import com.example.mealmate.view.util.ChipGroupUtil
import com.example.mealmate.viewmodel.PreferenceField
import com.example.mealmate.viewmodel.SurveyViewModel

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
        super.onViewCreated(view, savedInstanceState)
        viewModel.currentPreferenceField.postValue(PreferenceField.EXCEPTIONS)

        viewModel.getPreferencesByField(PreferenceField.EXCEPTIONS.name) { chipList ->

            viewModel.getPreferencesByField(PreferenceField.EXCEPTIONS.name) { userPreferenceList ->
                ChipGroupUtil().generateChipGroupByPreferences(
                    context = requireContext(),
                    scope = lifecycleScope,
                    userPreferenceList = userPreferenceList,
                    selectedPreferenceList = viewModel.exceptions.value ?: emptyList(),
                    layoutInflater = layoutInflater,
                    chipGroup = binding.exceptionsChipGroup,
                    onRemoveItem = { item -> viewModel.exceptions.value?.remove(item) },
                    onAddItem = { item -> viewModel.exceptions.value?.add(item) }
                )
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
