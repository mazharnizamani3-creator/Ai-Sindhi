package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Agriculture
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.BugReport
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.local.AgriGuideEntity
import com.example.ui.components.AjrakEmblemIcon
import com.example.ui.theme.AjrakDarkMaroon
import com.example.ui.theme.AjrakDeepBlue
import com.example.ui.theme.AjrakGold
import com.example.ui.theme.AjrakIndigo
import com.example.ui.theme.AjrakMaroon
import com.example.ui.theme.AjrakRosePetal
import com.example.ui.viewmodel.SindhiAssistantViewModel

@Composable
fun AgriKnowledgeScreen(
    viewModel: SindhiAssistantViewModel,
    onNavigateToChatWithPrompt: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val guides by viewModel.filteredGuides.collectAsState()
    val selectedCategory by viewModel.selectedAgriCategory.collectAsState()
    val searchQuery by viewModel.agriSearchQuery.collectAsState()

    val categories = listOf(
        "سڀ",
        "حشرات ۽ بيماريون",
        "فصل ۽ پوک",
        "زمين ۽ پاڻي",
        "باغ ۽ ميوا",
        "ثقافت ۽ تاريخ"
    )

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
                            text = "زرعي ۽ حشرات جي ماهرانہ رهنمائي",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                        )
                        Text(
                            text = "سنڌ جي فصلن، بيمارين ۽ جيتن جو مڪمل حل",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = AjrakGold,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }

            // Search Box
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { viewModel.onAgriSearchChange(it) },
                placeholder = {
                    Text(
                        text = "فصل يا بيماريءَ جو نالو ڳوليو (مثال: ڪپهه، گلابي ڪينچي، ڪڻڪ)...",
                        style = MaterialTheme.typography.bodyMedium.copy(fontSize = 13.sp, color = Color.Gray)
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "ڳولا",
                        tint = AjrakMaroon
                    )
                },
                trailingIcon = {
                    if (searchQuery.isNotEmpty()) {
                        IconButton(onClick = { viewModel.onAgriSearchChange("") }) {
                            Icon(
                                imageVector = Icons.Default.Clear,
                                contentDescription = "صاف ڪريو",
                                tint = Color.Gray
                            )
                        }
                    }
                },
                shape = RoundedCornerShape(16.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = AjrakMaroon,
                    unfocusedBorderColor = AjrakGold.copy(alpha = 0.4f),
                    focusedContainerColor = Color.White,
                    unfocusedContainerColor = Color.White
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 8.dp)
                    .testTag("agri_search_input")
            )

            // Category Filter Chips
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(horizontal = 14.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                items(categories) { cat ->
                    val isSelected = selectedCategory == cat
                    FilterChip(
                        selected = isSelected,
                        onClick = { viewModel.onCategorySelected(cat) },
                        label = {
                            Text(
                                text = cat,
                                style = MaterialTheme.typography.labelLarge.copy(
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 13.sp
                                )
                            )
                        },
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = AjrakMaroon,
                            selectedLabelColor = Color.White,
                            containerColor = Color.White,
                            labelColor = AjrakDarkMaroon
                        ),
                        border = FilterChipDefaults.filterChipBorder(
                            enabled = true,
                            selected = isSelected,
                            borderColor = if (isSelected) AjrakMaroon else AjrakGold.copy(alpha = 0.5f),
                            borderWidth = 1.dp
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Guide Cards List
            if (guides.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.BugReport,
                            contentDescription = null,
                            tint = AjrakMaroon.copy(alpha = 0.4f),
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "هن ڳولا بابت ڪو گائيڊ نه مليو.",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                color = Color.Gray,
                                fontWeight = FontWeight.Medium
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextButton(
                            onClick = {
                                onNavigateToChatWithPrompt(searchQuery)
                            }
                        ) {
                            Text(
                                text = "AI داناءُ کان سڌو سنئون پڇو",
                                color = AjrakMaroon,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 14.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    contentPadding = PaddingValues(top = 8.dp, bottom = 24.dp)
                ) {
                    items(guides, key = { it.id }) { guide ->
                        AgriGuideExpandableCard(
                            guide = guide,
                            onBookmarkClick = { viewModel.toggleGuideBookmark(guide) },
                            onAskAiClick = {
                                onNavigateToChatWithPrompt("مون کي هن بابت وڌيڪ ٻڌايو: ${guide.titleSindhi}")
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun AgriGuideExpandableCard(
    guide: AgriGuideEntity,
    onBookmarkClick: () -> Unit,
    onAskAiClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(targetValue = if (expanded) 180f else 0f, label = "expand_arrow")

    val categoryIcon: ImageVector = when {
        guide.categorySindhi.contains("حشرات") -> Icons.Default.BugReport
        guide.categorySindhi.contains("فصل") -> Icons.Default.Agriculture
        guide.categorySindhi.contains("پاڻي") -> Icons.Default.WaterDrop
        else -> Icons.Default.Eco
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = Brush.horizontalGradient(listOf(AjrakGold.copy(alpha = 0.5f), AjrakMaroon.copy(alpha = 0.2f)))
        ),
        modifier = Modifier
            .fillMaxWidth()
            .testTag("agri_guide_card_${guide.id}")
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.weight(1f)
                ) {
                    Box(
                        modifier = Modifier
                            .size(38.dp)
                            .clip(CircleShape)
                            .background(AjrakMaroon.copy(alpha = 0.1f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = categoryIcon,
                            contentDescription = null,
                            tint = AjrakMaroon,
                            modifier = Modifier.size(22.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = guide.titleSindhi,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Bold,
                                color = AjrakDarkMaroon,
                                fontSize = 15.sp,
                                lineHeight = 22.sp
                            )
                        )
                        Text(
                            text = guide.categorySindhi,
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = AjrakIndigo,
                                fontSize = 11.sp
                            )
                        )
                    }
                }

                Row {
                    IconButton(onClick = onBookmarkClick, modifier = Modifier.size(32.dp)) {
                        Icon(
                            imageVector = if (guide.isBookmarked) Icons.Default.Bookmark else Icons.Default.BookmarkBorder,
                            contentDescription = "محفوظ ڪريو",
                            tint = if (guide.isBookmarked) AjrakGold else Color.Gray,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    IconButton(
                        onClick = { expanded = !expanded },
                        modifier = Modifier.size(32.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.ExpandMore,
                            contentDescription = "کولو",
                            tint = AjrakMaroon,
                            modifier = Modifier
                                .size(24.dp)
                                .rotate(rotationState)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Short Summary
            Text(
                text = guide.shortSummary,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = Color(0xFF333333),
                    fontSize = 13.5.sp,
                    lineHeight = 22.sp
                )
            )

            // Expanded Details Section
            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 12.dp)) {
                    HorizontalDivider(color = Color(0xFFEFEFEF), thickness = 1.dp)
                    Spacer(modifier = Modifier.height(10.dp))

                    // Full Content
                    Text(
                        text = guide.fullContent,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = Color(0xFF1E1E1E),
                            fontSize = 13.5.sp,
                            lineHeight = 24.sp
                        )
                    )

                    if (guide.symptoms.isNotBlank()) {
                        Spacer(modifier = Modifier.height(8.dp))
                        SectionBadge(label = "بيماريءَ جون علامتون", value = guide.symptoms, color = AjrakMaroon)
                    }

                    if (guide.remedies.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        SectionBadge(label = "تدارڪ ۽ سنڀال", value = guide.remedies, color = AjrakIndigo)
                    }

                    if (guide.chemicalControl.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        SectionBadge(label = "ڪيميائي دوائون (Chemicals)", value = guide.chemicalControl, color = AjrakDarkMaroon)
                    }

                    if (guide.organicControl.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        SectionBadge(label = "حياتياتي / نيم جو نسخو", value = guide.organicControl, color = Color(0xFF2E7D32))
                    }

                    if (guide.bestSeason.isNotBlank()) {
                        Spacer(modifier = Modifier.height(6.dp))
                        SectionBadge(label = "بهترين موسم", value = guide.bestSeason, color = AjrakGold)
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        TextButton(
                            onClick = onAskAiClick,
                            modifier = Modifier
                                .clip(RoundedCornerShape(20.dp))
                                .background(AjrakMaroon.copy(alpha = 0.08f))
                        ) {
                            Icon(
                                imageVector = Icons.Default.Chat,
                                contentDescription = null,
                                tint = AjrakMaroon,
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(
                                text = "هن بابت AI کان وڌيڪ پڇو",
                                color = AjrakMaroon,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SectionBadge(label: String, value: String, color: Color) {
    Surface(
        shape = RoundedCornerShape(8.dp),
        color = color.copy(alpha = 0.06f),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(color.copy(alpha = 0.3f), color.copy(alpha = 0.1f)))),
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)) {
            Text(
                text = label,
                style = MaterialTheme.typography.labelMedium.copy(
                    color = color,
                    fontWeight = FontWeight.Bold,
                    fontSize = 11.5.sp
                )
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF2B2B2B),
                    fontSize = 12.5.sp,
                    lineHeight = 18.sp
                )
            )
        }
    }
}
