package com.sunildhiman90.composenavigation3

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator
import kotlinx.serialization.Serializable

@Serializable
data object Tabs : NavKey

@Serializable
data class ListDetail(val id: String) : NavKey


@Composable
fun Nav3BottomBarSeparateNavigation() {

    val nav3ViewModel = viewModel<Nav3ViewModel>()
    val mainBackStack = nav3ViewModel.mainBackStack

    NavDisplay(
        backStack = mainBackStack,
        entryDecorators = listOf(
            rememberSceneSetupNavEntryDecorator(),
            rememberSavedStateNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator()
        ),
        onBack = { mainBackStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<Tabs> {
                TabsScreen { id ->
                    mainBackStack.add(ListDetail(id = id))
                }
            }
            entry<ListDetail> {
                val id = it.id
                Detail("Detail Screen") {
                    Column {
                        Text("Detail Id: $id")
                        Button(onClick = {
                            mainBackStack.removeLastOrNull()
                        }) {
                            Text("Go Back")
                        }
                    }
                }
            }
        },
//            transitionSpec = {
//                slideInHorizontally(initialOffsetX = { it }) togetherWith slideOutHorizontally(
//                    targetOffsetX = { -it })
//            },
//            popTransitionSpec = {
//                slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(
//                    targetOffsetX = { it })
//            },
//            predictivePopTransitionSpec = {
//                slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(
//                    targetOffsetX = { it })
//            }
    )

}


@Composable
fun TabsScreen(
    goToDetail: ((String) -> Unit)? = null
) {

    val nav3ViewModel = viewModel<Nav3ViewModel>()
    val topLevelBackStack = nav3ViewModel.bottomBarBackStack.value


    Scaffold(
        bottomBar = {
            NavigationBar {
                TOP_LEVEL_ROUTES.forEach { route ->
                    val isSelected = route == topLevelBackStack.topLevelKey
                    NavigationBarItem(
                        selected = isSelected,
                        onClick = {
                            topLevelBackStack.addTopLevel(route)
                        },
                        icon = {
                            Icon(
                                imageVector = route.icon,
                                contentDescription = null
                            )
                        }
                    )

                }
            }
        }
    ) { contentPadding ->

        NavDisplay(
            modifier = Modifier
                .padding(contentPadding)
                .consumeWindowInsets(WindowInsets.statusBars),
            backStack = topLevelBackStack.backStack,
            entryDecorators = listOf(
                rememberSceneSetupNavEntryDecorator(),
                rememberSavedStateNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator()
            ),
            onBack = { topLevelBackStack.removeLast() },
            entryProvider = entryProvider {
                entry<Home> {
                    Home("Home Screen")
                }
                entry<ListScreen> {
                    ListScreenUi("ListScreen", onGoToDetail = {
                        goToDetail?.invoke("1")
                    })
                }
                entry<Profile> {
                    Profile("Profile Screen")
                }

            },
//            transitionSpec = {
//                slideInHorizontally(initialOffsetX = { it }) togetherWith slideOutHorizontally(
//                    targetOffsetX = { -it })
//            },
//            popTransitionSpec = {
//                slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(
//                    targetOffsetX = { it })
//            },
//            predictivePopTransitionSpec = {
//                slideInHorizontally(initialOffsetX = { -it }) togetherWith slideOutHorizontally(
//                    targetOffsetX = { it })
//            }
        )

    }
}

