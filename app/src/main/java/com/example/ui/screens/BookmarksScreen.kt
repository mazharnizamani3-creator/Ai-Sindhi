package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkRemove
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AjrakEmblemIcon
import com.example.ui.theme.AjrakDarkMaroon
import com.example.ui.theme.AjrakGold
import com.example.ui.theme.AjrakMaroon
import com.example.ui.viewmodel.SindhiAssistantViewModel

@Composable
fun BookmarksScreen(
    viewModel: SindhiAssistantViewModel,
    modifier: Modifier = Modifier
) {
    val bookmarkedMessages by viewModel.bookmarkedMessages.collectAsState()

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFFDFBF7))
        ) {
            // Header
            Surface(
                modifier = Modifier.fillMaxWidth(),
                color = AjrakMaroon,
                shadowElevation = 4.dp
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AjrakEmblemIcon(sizeDp = 36)
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "محفوظ ڪيل رهنمائي (Bookmarks)",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                        )
                        Text(
                            text = "ضروري زرعي حل ۽ محفوظ جواب",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = AjrakGold,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }

            if (bookmarkedMessages.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.Bookmark,
                            contentDescription = null,
                            tint = AjrakGold.copy(alpha = 0.5f),
                            modifier = Modifier.size(54.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "اڃا تائين ڪو به پيغام محفوظ ناهي ڪيو ويو.",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp)
                ) {
                    items(bookmarkedMessages, key = { it.id }) { msg ->
                        Card(
                            shape = RoundedCornerShape(14.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = CardDefaults.outlinedCardBorder().copy(
                                brush = Brush.horizontalGradient(listOf(AjrakGold, AjrakMaroon.copy(alpha = 0.3f)))
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(14.dp)) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = if (msg.isUser) "سوال" else "AI جواب",
                                        style = MaterialTheme.typography.labelMedium.copy(
                                            color = if (msg.isUser) AjrakDarkMaroon else AjrakMaroon,
                                            fontWeight = FontWeight.Bold
                                        )
                                    )
                                    IconButton(
                                        onClick = { viewModel.toggleMessageBookmark(msg) },
                                        modifier = Modifier.size(28.dp)
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.BookmarkRemove,
                                            contentDescription = "ختم ڪريو",
                                            tint = AjrakMaroon
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(6.dp))

                                Text(
                                    text = msg.messageText,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color(0xFF222222),
                                        lineHeight = 24.sp,
                                        fontSize = 14.sp
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
