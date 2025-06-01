package com.sunildhiman90.composenavigation3

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.navigation3.runtime.rememberNavBackStack

class Nav3ViewModel: ViewModel() {

    val backStack = mutableStateListOf<Any>(Nav3List)
}