package com.sunildhiman90.composenavigation3

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation3.runtime.rememberNavBackStack

class Nav3DetailViewModel: ViewModel() {

    init {
        println("Nav3DetailViewModel init")
    }

    override fun onCleared() {
        super.onCleared()
        println("Nav3DetailViewModel onCleared")
    }
}