package com.example.ui.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.example.ui.theme.AjrakDarkMaroon
import com.example.ui.theme.AjrakDeepBlue
import com.example.ui.theme.AjrakGold
import com.example.ui.theme.AjrakIndigo
import com.example.ui.theme.AjrakMaroon
import com.example.ui.theme.AjrakRosePetal
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin
import kotlin.random.Random

data class RosePetal(
    val initialX: Float,
    val initialY: Float,
    val size: Float,
    val speed: Float,
    val rotationSpeed: Float,
    val color: Color
)

@Composable
fun AjrakPatternBackground(
    modifier: Modifier = Modifier,
    enablePetals: Boolean = true,
    content: @Composable () -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "petals_and_pulse")
    
    val petalProgress by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 14000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "petal_fall"
    )

    val shimmer by infiniteTransition.animateFloat(
        initialValue = 0.85f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 4000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "shimmer"
    )

    val petals = remember {
        val random = Random(42)
        List(14) {
            RosePetal(
                initialX = random.nextFloat(),
                initialY = random.nextFloat(),
                size = random.nextFloat() * 14f + 10f,
                speed = random.nextFloat() * 0.5f + 0.5f,
                rotationSpeed = random.nextFloat() * 360f,
                color = if (random.nextBoolean()) AjrakRosePetal.copy(alpha = 0.18f) else AjrakGold.copy(alpha = 0.15f)
            )
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height

            // Background subtle grid and Ajrak stars
            val tileSize = 90.dp.toPx()
            val cols = (canvasWidth / tileSize).toInt() + 1
            val rows = (canvasHeight / tileSize).toInt() + 1

            for (r in 0..rows) {
                for (c in 0..cols) {
                    val cx = c * tileSize
                    val cy = r * tileSize
                    drawSubtleAjrakMotif(cx, cy, tileSize * 0.45f * shimmer)
                }
            }

            // Floating soft rose petals
            if (enablePetals) {
                for (petal in petals) {
                    val currentY = ((petal.initialY + petalProgress * petal.speed) % 1.0f) * canvasHeight
                    val sway = sin(petalProgress * 2 * PI + petal.initialX * 10).toFloat() * 24.dp.toPx()
                    val currentX = (petal.initialX * canvasWidth + sway) % canvasWidth

                    drawRosePetal(
                        centerX = currentX,
                        centerY = currentY,
                        radius = petal.size,
                        rotation = (petalProgress * petal.rotationSpeed) % 360f,
                        color = petal.color
                    )
                }
            }
        }

        content()
    }
}

private fun DrawScope.drawSubtleAjrakMotif(cx: Float, cy: Float, radius: Float) {
    // 8 pointed star path
    val path = Path()
    val outerR = radius
    val innerR = radius * 0.48f
    val points = 16

    for (i in 0 until points) {
        val angle = i * (2 * PI / points) - (PI / 2)
        val r = if (i % 2 == 0) outerR else innerR
        val x = cx + (r * cos(angle)).toFloat()
        val y = cy + (r * sin(angle)).toFloat()

        if (i == 0) path.moveTo(x, y) else path.lineTo(x, y)
    }
    path.close()

    drawPath(
        path = path,
        color = AjrakMaroon.copy(alpha = 0.035f),
        style = Fill
    )
    drawPath(
        path = path,
        color = AjrakGold.copy(alpha = 0.05f),
        style = Stroke(width = 1f)
    )

    // Center small rosette circle
    drawCircle(
        color = AjrakIndigo.copy(alpha = 0.04f),
        radius = radius * 0.22f,
        center = Offset(cx, cy)
    )
}

private fun DrawScope.drawRosePetal(
    centerX: Float,
    centerY: Float,
    radius: Float,
    rotation: Float,
    color: Color
) {
    val path = Path().apply {
        moveTo(centerX, centerY - radius)
        cubicTo(
            centerX + radius * 0.9f, centerY - radius * 0.4f,
            centerX + radius * 0.8f, centerY + radius * 0.8f,
            centerX, centerY + radius
        )
        cubicTo(
            centerX - radius * 0.8f, centerY + radius * 0.8f,
            centerX - radius * 0.9f, centerY - radius * 0.4f,
            centerX, centerY - radius
        )
        close()
    }

    drawPath(path = path, color = color, style = Fill)
}

@Composable
fun AjrakHeaderBanner(
    modifier: Modifier = Modifier,
    title: String = "سنڌي داناءُ AI",
    subtitle: String = "جي سائين! حڪم ڪريو."
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(64.dp)
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(AjrakDarkMaroon, AjrakMaroon, AjrakIndigo, AjrakDeepBlue)
                )
            )
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val h = size.height
            val w = size.width
            // Decorative gold border
            drawLine(
                color = AjrakGold,
                start = Offset(0f, h - 2.dp.toPx()),
                end = Offset(w, h - 2.dp.toPx()),
                strokeWidth = 2.dp.toPx()
            )
            // Small Ajrak stars pattern on edge
            var x = 16.dp.toPx()
            while (x < w) {
                drawCircle(
                    color = AjrakGold.copy(alpha = 0.4f),
                    radius = 2.5.dp.toPx(),
                    center = Offset(x, h - 6.dp.toPx())
                )
                x += 24.dp.toPx()
            }
        }
    }
}

@Composable
fun AjrakEmblemIcon(
    modifier: Modifier = Modifier,
    sizeDp: Int = 40
) {
    Canvas(modifier = modifier.size(sizeDp.dp)) {
        val cx = size.width / 2f
        val cy = size.height / 2f
        val r = size.width / 2.2f

        // Outer Maroon Circle
        drawCircle(
            brush = Brush.radialGradient(
                colors = listOf(AjrakMaroon, AjrakDarkMaroon)
            ),
            radius = r,
            center = Offset(cx, cy)
        )

        // Golden 8 pointed star
        val starPath = Path()
        val innerR = r * 0.45f
        val points = 16
        for (i in 0 until points) {
            val angle = i * (2 * PI / points) - (PI / 2)
            val rad = if (i % 2 == 0) r * 0.85f else innerR
            val x = cx + (rad * cos(angle)).toFloat()
            val y = cy + (rad * sin(angle)).toFloat()
            if (i == 0) starPath.moveTo(x, y) else starPath.lineTo(x, y)
        }
        starPath.close()

        drawPath(path = starPath, color = AjrakGold, style = Fill)
        drawPath(path = starPath, color = Color.White.copy(alpha = 0.8f), style = Stroke(width = 1.5.dp.toPx()))

        // Center Pearl
        drawCircle(color = AjrakDeepBlue, radius = r * 0.28f, center = Offset(cx, cy))
        drawCircle(color = Color.White, radius = r * 0.12f, center = Offset(cx, cy))
    }
}
