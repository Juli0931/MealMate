package com.example.mealmate.view.util

import android.content.Context
import com.example.mealmate.R

fun String.toStorageURL(context:Context):String =
    "${context.getString(R.string.storageURL)}/${this}.png"

