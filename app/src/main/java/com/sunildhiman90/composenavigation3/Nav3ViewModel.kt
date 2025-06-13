package com.sunildhiman90.composenavigation3

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel

class Nav3ViewModel: ViewModel() {

    val backStack = mutableStateListOf<Any>(Nav3List)

    val mainBackStack = mutableStateListOf<Any>(Tabs)

    val bottomBarBackStack = mutableStateOf(BottomBarBackStack<Any>(Home))

}