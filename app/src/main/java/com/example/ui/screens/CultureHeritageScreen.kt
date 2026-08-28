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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoStories
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Diamond
import androidx.compose.material.icons.filled.HistoryEdu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.AjrakEmblemIcon
import com.example.ui.theme.AjrakDarkMaroon
import com.example.ui.theme.AjrakDeepBlue
import com.example.ui.theme.AjrakGold
import com.example.ui.theme.AjrakIndigo
import com.example.ui.theme.AjrakMaroon

data class ShahBait(
    val sur: String,
    val baitSindhi: String,
    val meaningSindhi: String
)

data class SindhiProverb(
    val proverb: String,
    val explanation: String
)

@Composable
fun CultureHeritageScreen(
    onAskAiClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember { mutableIntStateOf(0) }
    val tabTitles = listOf("شاهه جو رسالو", "سنڌي پهاڪا", "اجرڪ جا 16 مرحلا", "تاريخي ماڳ")

    val baits = listOf(
        ShahBait(
            sur = "سر سارنگ (ٻاجهه ۽ مينهن جو سر)",
            baitSindhi = "آيو واءُ اتر جو، مينهن ڪيا وڏا وڏڦڙا،\nسارنگ کي سارنگ چئي، حيران ٿيا هاري سڀئي،\nسنڌ تي سدائين ڪرين مٿان مهر موليٰ!",
            meaningSindhi = "مفهوم: اي رب پاڪ! جڏهن اتر جو واءُ ۽ وسڪارو ٿئي ٿو، ته سڀ هاري خوشيءَ مان حيران ٿي وڃن ٿا. يا الله! سڄي دنيا ۽ خاص ڪري منهنجي مٺي سنڌ تي سدائين پنهنجي رحمت ۽ برڪت وساءِ."
        ),
        ShahBait(
            sur = "سر مارئي (وطن سان محبت)",
            baitSindhi = "پکا ۽ پنهوار، ڏٺي مون ڏينهن ٿيا،\nساريندي سنڀار، مون تن منجهان نه نڪري!",
            meaningSindhi = "مفهوم: مارئي چوي ٿي ته مون کي پنهنجن مسڪين ماروئڙن ۽ جهوپڙين کان پري گهڻو وقت گذري چڪو آهي، پر انهن جي ياد ۽ محبت هر پل منهنجي ساهه ۾ وسي ٿي."
        ),
        ShahBait(
            sur = "سر ڪلياڻ (وحدت ۽ شفا)",
            baitSindhi = "تون حبيب، تون طبيب، تون ئي دردن جي دوا،\nجانب منهنجي جيءَ ۾، آزار سڀ اولهيا!",
            meaningSindhi = "مفهوم: حقيقي محبوب (الله تعاليٰ) ئي دلين جو سچو طبيب آهي. سندس ياد ۽ نظر ڪرم سان ئي انسان جا سمورا روحاني ۽ جسماني ڏک دور ٿين ٿا."
        )
    )

    val proverbs = listOf(
        SindhiProverb(
            proverb = "جيڪو پوکي سو لڻي.",
            explanation = "انسان جهڙو عمل يا محنت ڪندو، اهڙو ئي ڦل ۽ نتيجو ماڻيندو."
        ),
        SindhiProverb(
            proverb = "ٻه مِٺا، ٽيون وِٺا.",
            explanation = "راز يا مصلحت ٻن ڄڻن تائين مناسب آهي، ٽئين تائين وڃڻ سان ڦهلجي وڃي ٿي."
        ),
        SindhiProverb(
            proverb = "پاڻي ڏسي پير لاهجي.",
            explanation = "ڪنهن به ڪم ۾ هٿ وجهڻ کان اڳ ان جا نتيجا ۽ حالات پرکڻ دانشمندي آهي."
        ),
        SindhiProverb(
            proverb = "پنهنجي گهوٽ، تڏهن ٻين جي ڄڃ.",
            explanation = "پنهنجي گهر ۽ ذميواري پهرين سنڀالڻ گهرجي."
        )
    )

    val ajrakStages = listOf(
        "1. کڙ (Khumbh): ڪپڙي کي پاڻي ۽ تيل ۾ ٻوڙي اس ۾ سڪائڻ.",
        "2. ساٿ (Saath): سجي مٽي ۽ کڙ سان ڪپڙو ڌوئڻ.",
        "3. کٽ (Khata): ٽامي جي ڪونڊن ۾ چن ۽ ڦٽڪي ملائي ڪپڙو صاف ڪرڻ.",
        "4. ٻور (Boor): ڪاٺ جي ٺپن سان مٽي جو پهريون ليپ لڳائڻ.",
        "5. ساک (Saakh): هيرس ڪاري رنگ جو پڪو ٺپو هڻڻ.",
        "6. گچ (Gach): چن ۽ ڳاڙهي مٽي سان ڇپائي ڪري بچاءُ واري پرت ٺاهڻ.",
        "7. نيل (Neel - Indigo): انڊيگو نير جي ديڳين ۾ ڪپڙو نيرو رنگڻ.",
        "8. ٻاٻرو رنڱ (Madder Dye): ال گوند ۽ ٻاٻرو جڙي ٻوٽين مان شاهوڪار ڳاڙهو رنگ چاڙهڻ."
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
                color = AjrakDarkMaroon,
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
                            text = "سنڌي ثقافت، ادب ۽ تاريخ",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = Color.White,
                                fontWeight = FontWeight.Bold,
                                fontSize = 17.sp
                            )
                        )
                        Text(
                            text = "شاهه لطيف جو رسالو، اجرڪ ۽ پهاڪا",
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = AjrakGold,
                                fontSize = 11.sp
                            )
                        )
                    }
                }
            }

            // Tab Row
            TabRow(
                selectedTabIndex = selectedTab,
                containerColor = Color.White,
                contentColor = AjrakMaroon,
                indicator = { tabPositions ->
                    TabRowDefaults.SecondaryIndicator(
                        modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                        color = AjrakMaroon,
                        height = 3.dp
                    )
                }
            ) {
                tabTitles.forEachIndexed { index, title ->
                    Tab(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Normal,
                                    fontSize = 12.sp,
                                    color = if (selectedTab == index) AjrakMaroon else Color.Gray
                                )
                            )
                        }
                    )
                }
            }

            // Content List
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 14.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(top = 12.dp, bottom = 24.dp)
            ) {
                when (selectedTab) {
                    0 -> {
                        items(baits) { bait ->
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = CardDefaults.outlinedCardBorder().copy(
                                    brush = Brush.horizontalGradient(listOf(AjrakGold, AjrakMaroon.copy(alpha = 0.3f)))
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(
                                            imageVector = Icons.Default.AutoStories,
                                            contentDescription = null,
                                            tint = AjrakMaroon,
                                            modifier = Modifier.size(20.dp)
                                        )
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = bait.sur,
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                color = AjrakMaroon,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 15.sp
                                            )
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(10.dp))

                                    Surface(
                                        color = Color(0xFFFDF7E7),
                                        shape = RoundedCornerShape(10.dp),
                                        modifier = Modifier.fillMaxWidth()
                                    ) {
                                        Text(
                                            text = bait.baitSindhi,
                                            style = MaterialTheme.typography.bodyLarge.copy(
                                                color = AjrakDarkMaroon,
                                                fontWeight = FontWeight.SemiBold,
                                                lineHeight = 28.sp,
                                                fontSize = 15.sp
                                            ),
                                            modifier = Modifier.padding(12.dp)
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Text(
                                        text = bait.meaningSindhi,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = Color(0xFF333333),
                                            lineHeight = 22.sp,
                                            fontSize = 13.5.sp
                                        )
                                    )

                                    Spacer(modifier = Modifier.height(8.dp))

                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.End
                                    ) {
                                        TextButton(
                                            onClick = { onAskAiClick("هن بيت جو مڪمل روحاني ۽ ادبي تفصيل ٻڌايو: ${bait.baitSindhi}") }
                                        ) {
                                            Icon(imageVector = Icons.Default.Chat, contentDescription = null, tint = AjrakMaroon, modifier = Modifier.size(16.dp))
                                            Spacer(modifier = Modifier.width(4.dp))
                                            Text("AI کان مڪمل تشريح پڇو", color = AjrakMaroon, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        }
                                    }
                                }
                            }
                        }
                    }
                    1 -> {
                        items(proverbs) { p ->
                            Card(
                                shape = RoundedCornerShape(14.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                border = CardDefaults.outlinedCardBorder().copy(
                                    brush = Brush.horizontalGradient(listOf(AjrakGold.copy(alpha = 0.5f), AjrakIndigo.copy(alpha = 0.3f)))
                                ),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(14.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Box(
                                            modifier = Modifier
                                                .size(24.dp)
                                                .clip(CircleShape)
                                                .background(AjrakIndigo),
                                            contentAlignment = Alignment.Center
                                        ) {
                                            Text("پ", color = Color.White, fontSize = 12.sp, fontWeight = FontWeight.Bold)
                                        }
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = p.proverb,
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                color = AjrakDarkMaroon,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 15.5.sp
                                            )
                                        )
                                    }

                                    Spacer(modifier = Modifier.height(6.dp))

                                    Text(
                                        text = p.explanation,
                                        style = MaterialTheme.typography.bodyMedium.copy(
                                            color = Color(0xFF444444),
                                            fontSize = 13.5.sp,
                                            lineHeight = 22.sp
                                        )
                                    )
                                }
                            }
                        }
                    }
                    2 -> {
                        item {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(imageVector = Icons.Default.Diamond, contentDescription = null, tint = AjrakMaroon)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "سنڌي اجرڪ جي تياري جا مرحلا",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                color = AjrakMaroon,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(12.dp))

                                    for (stage in ajrakStages) {
                                        Surface(
                                            color = Color(0xFFFDFBF7),
                                            shape = RoundedCornerShape(8.dp),
                                            border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(AjrakGold.copy(alpha = 0.3f), Color.Transparent))),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 3.dp)
                                        ) {
                                            Text(
                                                text = stage,
                                                style = MaterialTheme.typography.bodyMedium.copy(
                                                    color = AjrakDarkMaroon,
                                                    fontSize = 13.5.sp,
                                                    lineHeight = 22.sp
                                                ),
                                                modifier = Modifier.padding(10.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                    3 -> {
                        item {
                            Card(
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(containerColor = Color.White),
                                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Column(modifier = Modifier.padding(16.dp)) {
                                    Row(verticalAlignment = Alignment.CenterVertically) {
                                        Icon(imageVector = Icons.Default.HistoryEdu, contentDescription = null, tint = AjrakMaroon)
                                        Spacer(modifier = Modifier.width(8.dp))
                                        Text(
                                            text = "سنڌ جا قديم ۽ عظيم تاريخي ماڳ",
                                            style = MaterialTheme.typography.titleMedium.copy(
                                                color = AjrakMaroon,
                                                fontWeight = FontWeight.Bold,
                                                fontSize = 16.sp
                                            )
                                        )
                                    }
                                    Spacer(modifier = Modifier.height(10.dp))

                                    HistoricalPlaceItem(
                                        name = "موهن جو دڙو (Mohenjo-daro)",
                                        details = "لاڙڪاڻي ڀرسان سنڌو سڀيتا جو 5000 سال قديم شهري مرڪز جتي پڪيون سرون، وسيع حمام ۽ برتن سازي هئي."
                                    )
                                    HistoricalPlaceItem(
                                        name = "رني ڪوٽ (Ranikot Fort)",
                                        details = "سن پٽي ڀرسان دنيا جي وڏي ديوارن وارو قلعو، جنهن کي سنڌ جي عظيم ديوار به چيو وڃي ٿو."
                                    )
                                    HistoricalPlaceItem(
                                        name = "ڀنڀور (Bhambhore)",
                                        details = "سسئي پنهون جي قديم داستان ۽ سنڌ جي پراڻي بين الاقوامي سامونڊي بندرگاهه جو ماڳ."
                                    )
                                    HistoricalPlaceItem(
                                        name = "ڪوٽ ڏيجي (Kot Diji)",
                                        details = "خيرپور ۾ واقع قبل از تاريخ واري تھذيب جو شاهوڪار قلعو ۽ ٻڌائن جا آثار."
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun HistoricalPlaceItem(name: String, details: String) {
    Surface(
        color = Color(0xFFFDFBF7),
        shape = RoundedCornerShape(10.dp),
        border = CardDefaults.outlinedCardBorder().copy(brush = Brush.horizontalGradient(listOf(AjrakMaroon.copy(alpha = 0.2f), Color.Transparent))),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Text(
                text = name,
                style = MaterialTheme.typography.titleSmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = AjrakDarkMaroon,
                    fontSize = 14.5.sp
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = details,
                style = MaterialTheme.typography.bodySmall.copy(
                    color = Color(0xFF333333),
                    lineHeight = 20.sp,
                    fontSize = 13.sp
                )
            )
        }
    }
}
