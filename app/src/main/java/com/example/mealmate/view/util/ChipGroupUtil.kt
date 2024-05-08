package com.example.mealmate.view.util

import android.content.Context
import android.content.res.ColorStateList
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.example.mealmate.R
import com.example.mealmate.domain.model.UserPreference
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ChipGroupUtil {
    fun generateChipGroupByPreferences(
        context: Context,
        scope: CoroutineScope,
        userPreferenceList: List<UserPreference>,
        selectedPreferenceList: List<UserPreference>,
        layoutInflater: LayoutInflater,
        chipGroup: ChipGroup,
        onRemoveItem: (item: UserPreference) -> Unit,
        onAddItem: (item: UserPreference) -> Unit
    ) {
        userPreferenceList.forEach { userPreference ->
            val chip = layoutInflater.inflate(
                R.layout.user_preference_chip, chipGroup,
                false
            ) as Chip
            chipGroup.addView(chip)
            if (userPreference.name.compareUserPreference(selectedPreferenceList)) {

                chip.isChecked = true
                chip.chipBackgroundColor =  ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green))
                chip.setTextColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(
                            context,
                            R.color.black
                        )
                    )
                )

            }
            chip.text = userPreference.name
            chip.setOnCheckedChangeListener { _, isChecked ->
                chip.isChipIconVisible = !isChecked
                if (isChecked) {
                    onAddItem(userPreference)
                    scope.launch(Dispatchers.Main) {
                        delay(200)
                        chip.chipBackgroundColor =
                            ColorStateList.valueOf(ContextCompat.getColor(context, R.color.green))
                        chip.setTextColor(
                            ColorStateList.valueOf(
                                ContextCompat.getColor(
                                    context,
                                    R.color.black
                                )
                            )
                        )
                    }
                } else {
                    onRemoveItem(userPreference)
                    chip.chipBackgroundColor =
                        ColorStateList.valueOf(ContextCompat.getColor(context, R.color.dark_bg))
                    chip.setTextColor(
                        ColorStateList.valueOf(
                            ContextCompat.getColor(
                                context,
                                R.color.green
                            )
                        )
                    )
                }
            }
        }
    }


    fun String.compareUserPreference(userPreferenceList: List<UserPreference>): Boolean {
        for (item in userPreferenceList) {
            if (this == item.name) {
                return true
            }
        }
        return false
    }

}