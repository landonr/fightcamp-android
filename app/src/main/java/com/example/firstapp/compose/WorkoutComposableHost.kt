package com.example.firstapp.compose

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.rounded.AddCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.firstapp.compose.viewModels.ComposeItemViewModel
import com.example.firstapp.ui.theme.FirstAppTheme

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector,
)
val bottomNavItems = listOf(
    BottomNavItem(
        name = "Compose",
        route = "column",
        icon = Icons.Rounded.Home,
    ),
    BottomNavItem(
        name = "XML",
        route = "xmlColumn",
        icon = Icons.Rounded.AddCircle,
    )
)

@Composable
fun currentRoute(navController: NavHostController): String? {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    return navBackStackEntry?.destination?.route
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WorkoutComposableHost(
    navController: NavHostController,
    topBarState: MutableState<Boolean>,
    viewModel: ComposeItemViewModel = hiltViewModel()
) {
    when(currentRoute(navController = navController)) {
        "column" -> {
            topBarState.value = false
        }
        "detail/{itemId}" -> {
            topBarState.value = true
        }
        "xmlColumn" -> {
            topBarState.value = false
        }
    }
    Scaffold(
        topBar = {
            if (topBarState.value) {
                TopAppBar(
                    title = {
                        Text("My App")
                    },
                    navigationIcon = {
                        IconButton(onClick = { navController.navigateUp() }) {
                            Icon(
                                imageVector = Icons.Filled.ArrowBack,
                                contentDescription = "Back"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Cyan)
                )
            }
        }) { contentPadding ->
        FirstAppTheme {
            Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                NavHost(
                    navController = navController,
                    startDestination = "column"
                ) {
                    composable("column") {
                        WorkoutComposableColumn(
                            viewModel,
                            navController,
                            modifier = Modifier.padding(contentPadding)
                        )
                    }
                    composable("detail/{itemId}") { backStackEntry ->
                        val itemId = backStackEntry.arguments?.getString("itemId")
                        viewModel.result.value?.first { it.workout.id.toString() == itemId }?.let {
                            DetailActivity(navController, it)
                        }
                    }
                }
            }
        }
    }
}