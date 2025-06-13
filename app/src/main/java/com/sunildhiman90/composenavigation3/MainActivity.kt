package com.sunildhiman90.composenavigation3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.sunildhiman90.composenavigation3.ui.theme.ComposeNavigation3FullGuideTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeNavigation3FullGuideTheme {

                //Nav3Example1()
                //Nav3Example2()
                //Nav3BottomBarNestedNavigation()
                Nav3BottomBarSeparateNavigation()

            }
        }
    }
}