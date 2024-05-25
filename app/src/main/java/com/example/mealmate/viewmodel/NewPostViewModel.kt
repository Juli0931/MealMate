package com.example.mealmate.viewmodel

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.net.URI

class NewPostViewModel:ViewModel() {


    val tempCameraImage = MutableLiveData<Uri>()
    val imageSelected = MutableLiveData<Uri>()

}