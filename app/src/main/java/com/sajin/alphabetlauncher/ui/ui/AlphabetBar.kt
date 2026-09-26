package com.sajin.alphabetlauncher.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sajin.alphabetlauncher.utils.indexFromY
import com.sajin.alphabetlauncher.utils.letterFromIndex
import kotlin.math.exp
import kotlin.math.roundToInt

@Composable
fun AlphabetBar(
    selectedLetter: Char?,
    isDragging: Boolean,
    onLetterSelected: (Char) -> Unit,
    onDragStateChanged: (Boolean) -> Unit
) {

    val containerHeight = remember {
        mutableFloatStateOf(1f)
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .pointerInput(Unit) {

                detectDragGestures(

                    onDragStart = { offset ->

                        containerHeight.floatValue =
                            size.height.toFloat()

                        val index = indexFromY(
                            offset.y,
                            size.height.toFloat()
                        )

                        onLetterSelected(
                            letterFromIndex(index)
                        )

                        onDragStateChanged(true)
                    },

                    onDrag = { change, _ ->

                        change.consume()

                        containerHeight.floatValue =
                            size.height.toFloat()

                        val index = indexFromY(
                            change.position.y,
                            size.height.toFloat()
                        )

                        onLetterSelected(
                            letterFromIndex(index)
                        )
                    },

                    onDragEnd = {
                        onDragStateChanged(false)
                    },

                    onDragCancel = {
                        onDragStateChanged(false)
                    }
                )
            }
            .pointerInput(Unit) {

                detectTapGestures { offset ->

                    containerHeight.floatValue =
                        size.height.toFloat()

                    val index = indexFromY(
                        offset.y,
                        size.height.toFloat()
                    )

                    onLetterSelected(
                        letterFromIndex(index)
                    )

                    onDragStateChanged(true)
                }
            }
    ) {

        val top = 70f

        val bottom =
            containerHeight.floatValue - 70f

        val spacing =
            ((bottom - top) / 25f)
                .coerceAtLeast(1f)

        val selectedIndex =
            selectedLetter?.let {
                it.code - 'A'.code
            }

        for (i in 0 until 26) {

            val letter =
                letterFromIndex(i)

            val baseY =
                top + (i.toFloat() * spacing)

            val distance =
                if (selectedIndex != null) {
                    (i - selectedIndex).toFloat()
                } else {
                    100f
                }

            val sigma = 2.7f

            val influence =
                if (isDragging) {
                    exp(
                        -(distance * distance) /
                                (2f * sigma * sigma)
                    )
                } else {
                    0f
                }

            val maxShift = 115f

            val targetShift =
                if (isDragging) {
                    -maxShift * influence
                } else {
                    0f
                }

            val animatedShift by
            animateFloatAsState(
                targetValue = targetShift,
                animationSpec = spring(
                    dampingRatio = 0.8f,
                    stiffness = 600f
                ),
                label = "letterShift"
            )

            val yOffset =
                baseY -
                        containerHeight.floatValue / 2f

            Box(
                modifier = Modifier
                    .align(Alignment.CenterEnd)
                    .offset {
                        IntOffset(
                            x = animatedShift.roundToInt(),
                            y = yOffset.roundToInt()
                        )
                    }
                    .size(
                        if (
                            selectedLetter == letter &&
                            isDragging
                        ) {
                            48.dp
                        } else {
                            30.dp
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {

                if (
                    selectedLetter == letter &&
                    isDragging
                ) {

                    Box(
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        contentAlignment = Alignment.Center
                    ) {

                        Text(
                            text = letter.toString(),
                            color = Color.Black,
                            fontSize = 21.sp
                        )
                    }

                } else {

                    Text(
                        text = letter.toString(),
                        color = Color.White,
                        fontSize = 14.sp
                    )
                }
            }
        }

        // Star
        Text(
            text = "☆",
            color = Color.White,
            fontSize = 24.sp,
            modifier = Modifier.align(
                Alignment.TopEnd
            )
        )

        // Bottom dot
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .size(5.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}