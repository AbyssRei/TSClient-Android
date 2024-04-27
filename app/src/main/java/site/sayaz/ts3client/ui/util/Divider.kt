package site.sayaz.ts3client.ui.util

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp

@Composable
fun DotDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
) = Canvas(
    modifier
        .fillMaxWidth()
        .height(thickness)
) {
    val pathEffect = PathEffect.dashPathEffect(
        floatArrayOf(2f,6f), 0f)
    drawLine(
        color = color,
        start = Offset(0f, thickness.toPx() / 2),
        end = Offset(size.width, thickness.toPx() / 2),
        strokeWidth = thickness.toPx(),
        pathEffect = pathEffect,
        cap = StrokeCap.Round
    )
}

@Composable
fun DottedLineDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
    dashLength: Float = 10f,
    spaceLength: Float = 10f,
    dotLength: Float = 3f
) = Canvas(
    modifier
        .fillMaxWidth()
        .height(thickness)
) {
    val pathEffect = PathEffect.dashPathEffect(floatArrayOf(dashLength, spaceLength, dotLength, spaceLength), 0f)
    drawLine(
        color = color,
        start = Offset(0f, thickness.toPx() / 2),
        end = Offset(size.width, thickness.toPx() / 2),
        pathEffect = pathEffect,
        cap = StrokeCap.Round,
        strokeWidth = thickness.toPx()
    )
}
@Composable
fun DoubleDottedLineDivider(
    modifier: Modifier = Modifier,
    thickness: Dp = DividerDefaults.Thickness,
    color: Color = DividerDefaults.color,
    dashLength: Float = 10f,
    spaceLength: Float = 10f,
    dotLength: Float = 3f
) = Canvas(
    modifier
        .fillMaxWidth()
        .height(thickness)
) {
    val pathEffect = PathEffect.dashPathEffect(
        floatArrayOf(
            dashLength, spaceLength, dotLength, spaceLength, dotLength, spaceLength)
        , 0f)
    drawLine(
        color = color,
        start = Offset(0f, thickness.toPx() / 2),
        end = Offset(size.width, thickness.toPx() / 2),
        pathEffect = pathEffect,
        cap = StrokeCap.Round,
        strokeWidth = thickness.toPx()
    )
}

@Composable
@Preview
fun DividerPreview() {
    Column {
        Text(text = "line1")
        DotDivider()
        Text(text = "line2")
        DottedLineDivider()
        Text(text ="line3")
        DoubleDottedLineDivider()
    }
}
