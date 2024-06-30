package com.vavilon.compose.chart

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.vavilon.model.SourceCategories
import com.vavilon.model.states.SourceState
import com.vavilon.storage.local.entities.Source
import com.vavilon.ui.theme.Crimson
import com.vavilon.ui.theme.DodgerBlue
import com.vavilon.ui.theme.FireBrick
import com.vavilon.ui.theme.ForestGreen
import com.vavilon.ui.theme.IndianRed
import com.vavilon.ui.theme.LightGreen
import com.vavilon.ui.theme.LimeGreen
import com.vavilon.ui.theme.MediumBlue
import com.vavilon.ui.theme.MediumSeaGreen
import com.vavilon.ui.theme.OrangeRed
import com.vavilon.ui.theme.RoyalBlue
import com.vavilon.ui.theme.SkyBlue
import com.vavilon.ui.theme.SpringGreen
import com.vavilon.ui.theme.SteelBlue
import com.vavilon.ui.theme.Tomato
import com.vavilon.ui.theme.VavilonTheme

@Composable
fun PieChart(state: SourceState, modifier: Modifier = Modifier) {
    val incomeColorList: List<Color> = listOf(ForestGreen, MediumSeaGreen, SpringGreen, LimeGreen, LightGreen)
    val expenseColorList: List<Color> = listOf(Crimson, FireBrick, IndianRed, OrangeRed, Tomato)
    val savingsColorList: List<Color> = listOf(RoyalBlue, DodgerBlue, MediumBlue, SteelBlue, SkyBlue)

    val colorList = when (state.sourceCategory) {
        SourceCategories.INCOME -> incomeColorList
        SourceCategories.EXPENSE -> expenseColorList
        SourceCategories.SAVING -> savingsColorList
    }
    Row(
        modifier
            .fillMaxWidth()
            .height(150.dp),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    , verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = modifier
            .padding(top = 5.dp, start = 20.dp)
            .align(Alignment.CenterVertically)
        ) {
            Canvas(
                modifier = Modifier.size(100.dp),
                onDraw = {
                    val canvasSize = Size(size.width, size.height)
                    val center = Offset(canvasSize.width / 2, canvasSize.height / 2)
                    val radius = canvasSize.minDimension / 2
                    var startAngle = -90f
                    for (i in state.sourceList.indices) {
                        val sweepAngle = 360 * (state.sourceList[i].currentBalance / state.sourceList.sumOf { it.currentBalance })
                        drawArc(
                            color = colorList.getOrElse(i) { Color.Blue },
                            startAngle = startAngle,
                            sweepAngle = sweepAngle.toFloat(),
                            useCenter = true,
                            size = Size(radius * 2, radius * 2),
                            topLeft = Offset(center.x - radius, center.y - radius)
                        )
                        startAngle += sweepAngle.toFloat()
                    }
                }
            )
        }
        Column {
            state.sourceList.forEachIndexed { index, source ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minWidth = 80.dp, minHeight = 20.dp)
                        .padding(horizontal = 8.dp)
                        .clip(CircleShape)
                        .background(colorList.getOrElse(index) { VavilonTheme.colors.helpElement }),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = source.sourceTitle,
                        textAlign = TextAlign.Center,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewChart() {
    val source1  = Source("work", "work", "",2000.0)
    val source2  = Source("job", "work", "",3000.0)
    val source3  = Source("extra", "work", "",800.0)
    val source4  = Source("part time", "work", "",1000.0)
    val tempList = listOf(source1,source2,source3,source4)
    PieChart(state = SourceState())
}