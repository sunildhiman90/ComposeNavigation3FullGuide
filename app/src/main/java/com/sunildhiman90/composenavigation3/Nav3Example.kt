package com.sunildhiman90.composenavigation3

import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import kotlinx.serialization.Serializable


@Serializable
data object Nav3List : NavKey

@Serializable
data class Nav3Detail(val id: Int) : NavKey


@Composable
fun Nav3Example1() {

    //val backStack = remember { mutableStateListOf<Any>(Nav3List) }
    val backStack = rememberNavBackStack(Nav3List)

    NavDisplay(
        backStack = backStack,
        //onBack = { backStack.removeLastOrNull() },
        entryProvider = { key ->
            when (key) {
                is Nav3List -> NavEntry(key) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Button(
                            onClick = {
                                backStack.add(
                                    Nav3Detail(
                                        id = 1
                                    )
                                )
                            }
                        ) {
                            Text("Go to Detail")
                        }
                    }
                }

                is Nav3Detail -> NavEntry(key) {
                    val id = key.id
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("Product Id: $id")
                        Button(
                            onClick = {
                                backStack.removeLastOrNull()
                            }
                        ) {
                            Text("Go Back")
                        }
                    }
                }

                else -> throw IllegalStateException("unknown key")
            }
        }

    )
}

@Composable
fun Nav3Example2() {

    //val backStack = remember { mutableStateListOf<Any>(Nav3List) }
    //val backStack = rememberNavBackStack(Nav3List)
    //val viewmodel = viewModel<Nav3ViewModel>()
    //val backStack = viewmodel.backStack

    val backStack = rememberNavBackStack(Nav3List)
    NavDisplay(
        backStack = backStack,
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Nav3List>(

            ) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = {
                            backStack.add(
                                Nav3Detail(
                                    id = 1
                                )
                            )
                        }
                    ) {
                        Text("Go to Detail")
                    }
                }
            }

            entry<Nav3Detail>(
//                metadata = NavDisplay.transitionSpec {
//                    slideInVertically(initialOffsetY = { it }) togetherWith slideOutVertically(targetOffsetY = { -it })
//                }
            ) { entry ->
                val vm = viewModel<Nav3DetailViewModel>()
                val id = entry.id
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Product Id: $id")
                    Button(
                        onClick = {
                            backStack.removeLastOrNull()
                        }
                    ) {
                        Text("Go Back")
                    }
                }
            }

        },
        transitionSpec = {
            slideInHorizontally(initialOffsetX = { it }) togetherWith slideOutHorizontally(
                targetOffsetX = { -it })
        },
        popTransitionSpec = {
            slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(
                targetOffsetX = { it })
        },
        predictivePopTransitionSpec = {
            slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(
                targetOffsetX = { it })
        }

    )
}