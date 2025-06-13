package com.sunildhiman90.composenavigation3

import android.R.attr.top
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberSavedStateNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import androidx.navigation3.ui.rememberSceneSetupNavEntryDecorator


interface TopLevelRoute {
    val icon: ImageVector
}

data object Home : TopLevelRoute {
    override val icon = Icons.Default.Home
}

data object ListScreen : TopLevelRoute {
    override val icon = Icons.AutoMirrored.Filled.List
}

data object Profile : TopLevelRoute {
    override val icon = Icons.Default.AccountBox
}


val TOP_LEVEL_ROUTES: List<TopLevelRoute> = listOf(Home, ListScreen, Profile)

@Composable
fun Nav3BottomBarNestedNavigation() {

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
            modifier = Modifier.padding(contentPadding).consumeWindowInsets(WindowInsets.statusBars),
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
                        topLevelBackStack.add(ListDetail(id = "1"))
                    })
                }
                entry<Profile> {
                    Profile("Profile Screen")
                }

                entry<ListDetail> {
                    val id = it.id
                    Detail("Detail Screen") {

                        Column {
                            Text("Detail Id: $id")

                            Button(onClick = {
                                topLevelBackStack.removeLast()
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
}

@Composable
fun Home(
    title: String,
    modifier: Modifier = Modifier,
    content: (@Composable () -> Unit)? = null,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .clip(RoundedCornerShape(48.dp))
    ) {
        Title(title)
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content?.let {
                it()
            }
        }
    }
}


@Composable
fun ListScreenUi(
    title: String,
    modifier: Modifier = Modifier,
    onGoToDetail: (() -> Unit)? = null,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .clip(RoundedCornerShape(48.dp))
    ) {
        Title(title)
        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = {
                onGoToDetail?.invoke()
            }) {
                Text("Go to Detail")
            }
        }
    }
}

@Composable
fun Profile(
    title: String,
    modifier: Modifier = Modifier,
    content: (@Composable () -> Unit)? = null,
) {
    Home(
        title = title,
        modifier = modifier,
        content = content
    )
}

@Composable
fun Detail(
    title: String,
    modifier: Modifier = Modifier,
    content: (@Composable () -> Unit)? = null,
) {
    Home(
        title = title,
        modifier = modifier,
        content = content
    )
}

@Composable
fun Title(title: String) {
    Column(
        modifier = Modifier.height(56.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            text = title
        )
    }
}

//Where T is the type of the top level route
class BottomBarBackStack<T : Any>(startKey: T) {

    private var topLevelStacks: LinkedHashMap<T, SnapshotStateList<T>> = linkedMapOf(
        startKey to mutableStateListOf(startKey)
    )

    var topLevelKey by mutableStateOf(startKey)
        private set

    val backStack = mutableStateListOf(startKey)


    private fun updateBackStack() {
        backStack.apply {
            clear()

            addAll(topLevelStacks.flatMap {
                it.value
            })
        }
    }

    fun addTopLevel(key: T) {

        if (topLevelStacks[key] == null) {
            topLevelStacks.put(key, mutableStateListOf(key))
        } else {
            topLevelStacks.apply {
                val stack = remove(key)

                stack?.let {
                    put(key, it)
                }

            }
        }

        topLevelKey = key
        updateBackStack()

    }

    fun add(key: T) {

        topLevelStacks[topLevelKey]?.add(key)
        updateBackStack()
    }

    fun removeLast() {
        //simple nav entry or route
        val removedLastKey = topLevelStacks[topLevelKey]?.removeLastOrNull()

        //top level  route
        topLevelStacks.remove(removedLastKey)
        topLevelKey = topLevelStacks.keys.last()

        updateBackStack()
    }


}




