package com.example.ui.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.outlined.Agriculture
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.AutoStories
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material.icons.outlined.Chat
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AjrakPatternBackground
import com.example.ui.screens.AgriKnowledgeScreen
import com.example.ui.screens.BookmarksScreen
import com.example.ui.screens.ChatScreen
import com.example.ui.screens.CultureHeritageScreen
import com.example.ui.screens.ImageGeneratorScreen
import com.example.ui.theme.AjrakDarkMaroon
import com.example.ui.theme.AjrakDeepBlue
import com.example.ui.theme.AjrakGold
import com.example.ui.theme.AjrakIndigo
import com.example.ui.theme.AjrakMaroon
import com.example.ui.viewmodel.SindhiAssistantViewModel

sealed class Screen(
    val route: String,
    val titleSindhi: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector
) {
    object Chat : Screen("chat", "ڳالهه ٻولهه", Icons.Filled.Chat, Icons.Outlined.Chat)
    object AgriGuide : Screen("agri_guide", "زرعي رهنمائي", Icons.Filled.Agriculture, Icons.Outlined.Agriculture)
    object ImageStudio : Screen("image_studio", "تصوير ساز", Icons.Filled.AutoAwesome, Icons.Outlined.AutoAwesome)
    object Culture : Screen("culture", "ثقافت ۽ تاريخ", Icons.Filled.AutoStories, Icons.Outlined.AutoStories)
    object Bookmarks : Screen("bookmarks", "محفوظ", Icons.Filled.Bookmark, Icons.Outlined.Bookmark)
}

@Composable
fun MainAppNavigation(
    viewModel: SindhiAssistantViewModel,
    modifier: Modifier = Modifier
) {
    var currentScreen by rememberSaveable { mutableStateOf<String>(Screen.Chat.route) }

    val screens = listOf(
        Screen.Chat,
        Screen.AgriGuide,
        Screen.ImageStudio,
        Screen.Culture,
        Screen.Bookmarks
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        AjrakPatternBackground {
            Scaffold(
                containerColor = Color.Transparent,
                bottomBar = {
                    NavigationBar(
                        containerColor = AjrakMaroon,
                        contentColor = Color.White,
                        tonalElevation = 8.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(68.dp)
                            .testTag("bottom_navigation_bar")
                    ) {
                        screens.forEach { screen ->
                            val isSelected = currentScreen == screen.route
                            NavigationBarItem(
                                selected = isSelected,
                                onClick = { currentScreen = screen.route },
                                icon = {
                                    Icon(
                                        imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                                        contentDescription = screen.titleSindhi,
                                        modifier = Modifier.size(22.dp)
                                    )
                                },
                                label = {
                                    Text(
                                        text = screen.titleSindhi,
                                        style = MaterialTheme.typography.labelSmall.copy(
                                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                            fontSize = 11.sp
                                        )
                                    )
                                },
                                colors = NavigationBarItemDefaults.colors(
                                    selectedIconColor = AjrakDarkMaroon,
                                    selectedTextColor = AjrakGold,
                                    indicatorColor = AjrakGold,
                                    unselectedIconColor = Color.White.copy(alpha = 0.75f),
                                    unselectedTextColor = Color.White.copy(alpha = 0.75f)
                                ),
                                modifier = Modifier.testTag("nav_item_${screen.route}")
                            )
                        }
                    }
                }
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                ) {
                    when (currentScreen) {
                        Screen.Chat.route -> {
                            ChatScreen(viewModel = viewModel)
                        }
                        Screen.AgriGuide.route -> {
                            AgriKnowledgeScreen(
                                viewModel = viewModel,
                                onNavigateToChatWithPrompt = { prompt ->
                                    viewModel.sendMessage(prompt)
                                    currentScreen = Screen.Chat.route
                                }
                            )
                        }
                        Screen.ImageStudio.route -> {
                            ImageGeneratorScreen(viewModel = viewModel)
                        }
                        Screen.Culture.route -> {
                            CultureHeritageScreen(
                                onAskAiClick = { query ->
                                    viewModel.sendMessage(query)
                                    currentScreen = Screen.Chat.route
                                }
                            )
                        }
                        Screen.Bookmarks.route -> {
                            BookmarksScreen(viewModel = viewModel)
                        }
                    }
                }
            }
        }
    }
}
