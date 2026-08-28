package com.example.ui.screens

import android.graphics.Bitmap
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.scaleIn
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.data.local.GeneratedImageEntity
import com.example.ui.components.AjrakEmblemIcon
import com.example.ui.theme.AjrakDarkMaroon
import com.example.ui.theme.AjrakDeepBlue
import com.example.ui.theme.AjrakGold
import com.example.ui.theme.AjrakIndigo
import com.example.ui.theme.AjrakMaroon
import com.example.ui.theme.AjrakRosePetal
import com.example.ui.viewmodel.ImageGenUiState
import com.example.ui.viewmodel.SindhiAssistantViewModel
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun ImageGeneratorScreen(
    viewModel: SindhiAssistantViewModel,
    modifier: Modifier = Modifier
) {
    val promptText by viewModel.imagePromptSindhi.collectAsState()
    val genState by viewModel.imageGenState.collectAsState()
    val allImages by viewModel.allGeneratedImages.collectAsState()
    var selectedPreviewImage by remember { mutableStateOf<GeneratedImageEntity?>(null) }

    val presetPrompts = listOf(
        Pair("سنڌ جي زرخيز ڪپهه جا کيت ۽ هاري", "Lush green cotton fields in Sindh Pakistan with farmer in Ajrak topi at golden sunrise"),
        Pair("روايتي سنڌي اجرڪ جو خوبصورت تارو", "Intricate traditional Sindhi Ajrak geometric star motif block print patterns"),
        Pair("موهن جو دڙو جو قديم شهري نظارو", "Ancient historical Mohenjo Daro Indus valley civilization architecture at dawn"),
        Pair("سنڌو درياه ۽ سنڌي ٻيڙيون", "Majestic Indus river at sunset with traditional Sindhi wooden boats"),
        Pair("ڪڻڪ جا سونهري سنگ ۽ سنڌي ثقافت", "Golden ripe wheat fields with authentic Sindhi cultural art"),
        Pair("سنڌڙي انب جا مٺا ميوا ۽ سرسبز باغ", "Ripe Sindhri mangoes on tree in sunny lush orchard in Sindh")
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .background(Color(0xFFFDFBF7)),
            contentPadding = PaddingValues(bottom = 32.dp)
        ) {
            // Header
            item {
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    color = AjrakIndigo,
                    shadowElevation = 4.dp
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(40.dp)
                                .clip(CircleShape)
                                .background(AjrakGold),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Palette,
                                contentDescription = null,
                                tint = AjrakDarkMaroon,
                                modifier = Modifier.size(24.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "تصوير ساز AI (AI Image Studio)",
                                style = MaterialTheme.typography.titleLarge.copy(
                                    color = Color.White,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 17.sp
                                )
                            )
                            Text(
                                text = "سنڌ جي سونهن، اجرڪ ۽ ثقافت جون تصويرون ٺاهيو",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = AjrakGold,
                                    fontSize = 11.sp
                                )
                            )
                        }
                    }
                }
            }

            // Input Section
            item {
                Card(
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    border = CardDefaults.outlinedCardBorder().copy(
                        brush = Brush.horizontalGradient(listOf(AjrakMaroon.copy(alpha = 0.3f), AjrakGold.copy(alpha = 0.5f)))
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(14.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "تصوير جو خيال يا تفصيل بيان ڪريو:",
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight.Bold,
                                color = AjrakDarkMaroon,
                                fontSize = 14.sp
                            )
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        OutlinedTextField(
                            value = promptText,
                            onValueChange = { viewModel.onImagePromptChange(it) },
                            placeholder = {
                                Text(
                                    text = "مثال: سنڌو درياه جي ڪپ تي ڳاڙهو اجرڪ ۽ سنڌي ٻيڙي...",
                                    style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray, fontSize = 13.sp)
                                )
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(100.dp)
                                .testTag("image_prompt_input"),
                            shape = RoundedCornerShape(12.dp),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = AjrakMaroon,
                                unfocusedBorderColor = AjrakGold.copy(alpha = 0.5f),
                                focusedContainerColor = Color(0xFFFDFBF7),
                                unfocusedContainerColor = Color(0xFFFDFBF7)
                            ),
                            textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 14.5.sp)
                        )

                        Spacer(modifier = Modifier.height(10.dp))

                        // Preset Prompt chips
                        Text(
                            text = "يا هيٺ ڏنل مشهور خيالن مان چونڊيو:",
                            style = MaterialTheme.typography.labelSmall.copy(color = Color.Gray, fontSize = 11.5.sp)
                        )

                        Spacer(modifier = Modifier.height(6.dp))

                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(6.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            items(presetPrompts) { (sindhi, eng) ->
                                SuggestionChip(
                                    onClick = {
                                        viewModel.onImagePromptChange(sindhi)
                                        viewModel.generateImage(sindhiPrompt = sindhi, englishPrompt = eng)
                                    },
                                    label = {
                                        Text(
                                            text = sindhi,
                                            style = MaterialTheme.typography.bodySmall.copy(fontSize = 12.sp, color = AjrakDarkMaroon)
                                        )
                                    },
                                    colors = SuggestionChipDefaults.suggestionChipColors(
                                        containerColor = AjrakGold.copy(alpha = 0.12f),
                                        labelColor = AjrakDarkMaroon
                                    ),
                                    border = SuggestionChipDefaults.suggestionChipBorder(
                                        enabled = true,
                                        borderColor = AjrakGold.copy(alpha = 0.6f),
                                        borderWidth = 1.dp
                                    ),
                                    shape = RoundedCornerShape(14.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(14.dp))

                        // Generate Button
                        Button(
                            onClick = {
                                viewModel.generateImage()
                            },
                            enabled = promptText.isNotBlank() && genState !is ImageGenUiState.Loading,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = AjrakMaroon,
                                contentColor = Color.White
                            ),
                            shape = RoundedCornerShape(22.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp)
                                .testTag("generate_image_button")
                        ) {
                            if (genState is ImageGenUiState.Loading) {
                                CircularProgressIndicator(
                                    color = Color.White,
                                    modifier = Modifier.size(20.dp),
                                    strokeWidth = 2.dp
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "جي سائين! تصوير ٺهي رهي آهي...",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                )
                            } else {
                                Icon(
                                    imageVector = Icons.Default.AutoAwesome,
                                    contentDescription = null,
                                    tint = AjrakGold,
                                    modifier = Modifier.size(18.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(
                                    text = "تصوير تيار ڪريو (Generate AI Image)",
                                    style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold, fontSize = 14.sp)
                                )
                            }
                        }
                    }
                }
            }

            // Current Generation Result
            item {
                when (val state = genState) {
                    is ImageGenUiState.Success -> {
                        Card(
                            shape = RoundedCornerShape(16.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White),
                            border = CardDefaults.outlinedCardBorder().copy(
                                brush = Brush.horizontalGradient(listOf(AjrakGold, AjrakMaroon))
                            ),
                            elevation = CardDefaults.cardElevation(defaultElevation = 3.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Column(
                                modifier = Modifier.padding(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = "✨ نئين تيار ٿيل تصوير:",
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        color = AjrakDarkMaroon,
                                        fontWeight = FontWeight.Bold
                                    )
                                )
                                Spacer(modifier = Modifier.height(8.dp))

                                if (state.bitmap != null) {
                                    Image(
                                        bitmap = state.bitmap.asImageBitmap(),
                                        contentDescription = state.imageEntity.promptSindhi,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(1f)
                                            .clip(RoundedCornerShape(12.dp))
                                    )
                                } else {
                                    SindhiArtCanvasView(
                                        prompt = state.imageEntity.promptSindhi,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(1f)
                                            .clip(RoundedCornerShape(12.dp))
                                    )
                                }

                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = state.imageEntity.promptSindhi,
                                    style = MaterialTheme.typography.bodyMedium.copy(
                                        color = Color.DarkGray,
                                        fontWeight = FontWeight.Medium,
                                        textAlign = TextAlign.Center
                                    )
                                )
                            }
                        }
                    }
                    is ImageGenUiState.Error -> {
                        Card(
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFFFEBEE)),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 14.dp, vertical = 6.dp)
                        ) {
                            Text(
                                text = state.messageSindhi,
                                color = Color.Red,
                                modifier = Modifier.padding(12.dp),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                    else -> {}
                }
            }

            // Gallery Section
            item {
                Spacer(modifier = Modifier.height(8.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "تصويري گيلري (Saved Sindhi AI Art)",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = AjrakDarkMaroon,
                            fontSize = 16.sp
                        )
                    )
                    Text(
                        text = "${allImages.size} تصويرون",
                        style = MaterialTheme.typography.labelSmall.copy(color = AjrakGold)
                    )
                }
            }

            if (allImages.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(180.dp)
                            .padding(horizontal = 14.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        SindhiArtCanvasView(
                            prompt = "سنڌ جي سونهن ۽ اجرڪ",
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(160.dp)
                                .clip(RoundedCornerShape(14.dp))
                        )
                    }
                }
            } else {
                items(allImages.chunked(2)) { pair ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 14.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        for (imageEntity in pair) {
                            Card(
                                shape = RoundedCornerShape(12.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = CardDefaults.outlinedCardBorder().copy(
                                    brush = Brush.horizontalGradient(listOf(AjrakGold.copy(alpha = 0.4f), AjrakMaroon.copy(alpha = 0.2f)))
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                modifier = Modifier
                                    .weight(1f)
                                    .clickable { selectedPreviewImage = imageEntity }
                            ) {
                                Column(modifier = Modifier.padding(8.dp)) {
                                    SindhiArtCanvasView(
                                        prompt = imageEntity.promptSindhi,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .aspectRatio(1f)
                                            .clip(RoundedCornerShape(8.dp))
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = imageEntity.promptSindhi,
                                        maxLines = 1,
                                        style = MaterialTheme.typography.bodySmall.copy(
                                            fontSize = 11.5.sp,
                                            fontWeight = FontWeight.Medium,
                                            color = AjrakDarkMaroon
                                        )
                                    )
                                }
                            }
                        }
                        if (pair.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
        }
    }

    // Full Screen Image Preview Dialog
    selectedPreviewImage?.let { img ->
        Dialog(onDismissRequest = { selectedPreviewImage = null }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "سنڌي AI آرٽ",
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = AjrakMaroon,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        IconButton(onClick = { selectedPreviewImage = null }) {
                            Icon(imageVector = Icons.Default.Close, contentDescription = "بند ڪريو")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    SindhiArtCanvasView(
                        prompt = img.promptSindhi,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                            .clip(RoundedCornerShape(12.dp))
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        text = img.promptSindhi,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = AjrakDarkMaroon,
                            textAlign = TextAlign.Center
                        )
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        IconButton(onClick = { viewModel.toggleImageFavorite(img) }) {
                            Icon(
                                imageVector = if (img.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                                contentDescription = "پسنديده",
                                tint = if (img.isFavorite) AjrakRosePetal else Color.Gray
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun SindhiArtCanvasView(
    prompt: String,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height
        val cx = w / 2f
        val cy = h / 2f

        // Background Rich Palette based on prompt
        val bgColors = when {
            prompt.contains("ڪپهه") || prompt.contains("فصل") || prompt.contains("کيت") ->
                listOf(Color(0xFF1B5E20), Color(0xFF2E7D32), Color(0xFF81C784), AjrakGold)
            prompt.contains("درياه") || prompt.contains("ٻيڙي") || prompt.contains("پاڻي") ->
                listOf(AjrakDeepBlue, AjrakIndigo, Color(0xFF0288D1), AjrakGold)
            prompt.contains("موهن") || prompt.contains("دڙو") || prompt.contains("تاريخ") ->
                listOf(Color(0xFF4E342E), Color(0xFF8D6E63), Color(0xFFD7CCC8), AjrakGold)
            else ->
                listOf(AjrakDarkMaroon, AjrakMaroon, AjrakIndigo, AjrakDeepBlue)
        }

        drawRect(
            brush = Brush.radialGradient(
                colors = bgColors,
                center = Offset(cx, cy),
                radius = w * 0.8f
            )
        )

        // Draw Ajrak geometric stars and motifs
        val starPath = Path()
        val r = w * 0.36f
        val innerR = r * 0.45f
        val points = 16

        for (i in 0 until points) {
            val angle = i * (2 * PI / points) - (PI / 2)
            val rad = if (i % 2 == 0) r else innerR
            val px = cx + (rad * cos(angle)).toFloat()
            val py = cy + (rad * sin(angle)).toFloat()
            if (i == 0) starPath.moveTo(px, py) else starPath.lineTo(px, py)
        }
        starPath.close()

        drawPath(path = starPath, color = AjrakGold.copy(alpha = 0.85f), style = Fill)
        drawPath(path = starPath, color = Color.White, style = Stroke(width = 2.dp.toPx()))

        // Inner rosette
        drawCircle(color = AjrakMaroon, radius = r * 0.42f, center = Offset(cx, cy))
        drawCircle(color = AjrakGold, radius = r * 0.22f, center = Offset(cx, cy))
        drawCircle(color = Color.White, radius = r * 0.08f, center = Offset(cx, cy))

        // Four corner Ajrak quadrant stars
        val cornerOffset = 24.dp.toPx()
        val cornerRadius = 14.dp.toPx()
        listOf(
            Offset(cornerOffset, cornerOffset),
            Offset(w - cornerOffset, cornerOffset),
            Offset(cornerOffset, h - cornerOffset),
            Offset(w - cornerOffset, h - cornerOffset)
        ).forEach { pos ->
            drawCircle(color = AjrakGold.copy(alpha = 0.6f), radius = cornerRadius, center = pos)
            drawCircle(color = Color.White, radius = cornerRadius * 0.4f, center = pos)
        }
    }
}
