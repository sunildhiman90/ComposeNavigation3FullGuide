package com.sunildhiman90.composenavigation3

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.navigation3.ListDetailSceneStrategy
import androidx.compose.material3.adaptive.navigation3.rememberListDetailSceneStrategy
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entry
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import kotlinx.serialization.Serializable

@Serializable
data object ItemsList : NavKey

@Serializable
data class ItemDetail(val id: String) : NavKey

@Serializable
data object ExtraScreen : NavKey


@OptIn(ExperimentalMaterial3AdaptiveApi::class)
@Composable
fun Nav3AdaptiveListDetail() {

    val backStack = rememberNavBackStack(ItemsList)
    val sceneStrategy = rememberListDetailSceneStrategy<Any>()

    Scaffold { paddingValues ->
        NavDisplay(
            backStack = backStack,
            modifier = Modifier
                .padding(paddingValues)
                .consumeWindowInsets(WindowInsets.statusBars),
            sceneStrategy = sceneStrategy,
            onBack = { keysToRemove -> repeat(keysToRemove) { backStack.removeLastOrNull() } },
            entryProvider = entryProvider {
                entry<ItemsList>(
                    metadata = ListDetailSceneStrategy.listPane(

                        //This will be displayed when there is no item selected in list
                        detailPlaceholder = {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .fillMaxSize()
                                    .background(Color.Yellow.copy(alpha = 0.4f))
                            ) {

                                Text("Choose a item from the list screen")

                            }
                        }
                    )

                ) {
                    ItemsListScreen(
                        title = "List Screen",
                        modifier = Modifier.background(Color.Green.copy(alpha = 0.4f))
                    ) {
                        backStack.add(ItemDetail(it))
                    }
                }
                entry<ItemDetail>(
                    metadata = ListDetailSceneStrategy.detailPane()
                ) { product ->
                    val id = product.id
                    ItemDetailScreen(
                        id = id,
                        modifier = Modifier.background(Color.Red.copy(alpha = 0.4f))
                    ) {
                        backStack.add(ExtraScreen)
                    }
                }
                entry<ExtraScreen>(
                    metadata = ListDetailSceneStrategy.extraPane()
                ) {
                    ExtraPaneScreen(
                        modifier = Modifier.background(Color.LightGray)
                    )
                }
            }
        )
    }

}

//1st pane
@Composable
fun ItemsListScreen(
    title: String,
    modifier: Modifier = Modifier,
    onGoToDetail: ((String) -> Unit)? = null,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxSize()
            .safeDrawingPadding()
            .clip(RoundedCornerShape(48.dp))
    ) {

        val itemsList = List(10) {
            "Item ${it + 1}"
        }
        Title(title)
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(
                itemsList, key = { it }
            ) { item ->
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onGoToDetail?.invoke(item)
                        },
                    colors = CardDefaults.cardColors(
                        containerColor = Color.Red.copy(alpha = 0.6f)
                    )
                ) {
                    Text(
                        color = Color.White,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = item
                    )
                }

            }

        }
    }
}

//2nd pane
@Composable
fun ItemDetailScreen(
    modifier: Modifier = Modifier,
    title: String = "Detail Screen",
    id: String,
    goToExtraScreen: () -> Unit = {}
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
            Text("Item Detail Id: $id")
            Button(onClick = goToExtraScreen) {
                Text("Go To Extra Screen")
            }
        }
    }
}

//Extra pane
@Composable
fun ExtraPaneScreen(
    modifier: Modifier = Modifier,
    title: String = "Extra Pane Screen",
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
            Text("Extra Pane Content")
        }
    }
}