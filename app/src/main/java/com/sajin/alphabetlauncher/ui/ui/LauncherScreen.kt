package com.sajin.alphabetlauncher.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.sajin.alphabetlauncher.LauncherViewModel
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun LauncherScreen(
    viewModel: LauncherViewModel
) {

    var currentTime by remember {
        mutableStateOf("")
    }

    var currentDate by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit) {

        while (true) {

            val now = Date()

            currentTime =
                SimpleDateFormat(
                    "HH:mm",
                    Locale.getDefault()
                ).format(now)

            currentDate =
                SimpleDateFormat(
                    "EEE, dd MMM yyyy",
                    Locale.getDefault()
                ).format(now)

            delay(1000)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {

        if (!viewModel.isDragging) {

            HomeContent(
                time = currentTime,
                date = currentDate
            )

        } else {

            val letter =
                viewModel.selectedLetter

            if (letter != null) {

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = 24.dp,
                            top = 50.dp,
                            end = 65.dp
                        )
                ) {

                    Text(
                        text = letter.toString(),
                        color = Color.White,
                        fontSize = 42.sp
                    )

                    Text(
                        text = "Apps",
                        color = Color.Gray,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(
                            bottom = 15.dp
                        )
                    )

                    AppList(
                        apps =
                            viewModel
                                .getAppsForLetter(
                                    letter
                                ),
                        onAppClick = {
                            viewModel.launchApp(it)
                        }
                    )
                }
            }
        }

        AlphabetBar(
            selectedLetter =
                viewModel.selectedLetter,

            isDragging =
                viewModel.isDragging,

            onLetterSelected = {
                viewModel.selectLetter(it)
            },
            onDragStateChanged = {
                viewModel.updateDragging(it)
            }

        )
    }
}

@Composable
private fun HomeContent(
    time: String,
    date: String
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 28.dp,
                top = 70.dp,
                end = 70.dp
            ),

        verticalArrangement =
            Arrangement.Top
    ) {

        Text(
            text = time,
            color = Color.White,
            fontSize = 54.sp
        )

        Text(
            text = date,
            color = Color.Gray,
            fontSize = 17.sp,
            modifier = Modifier.padding(
                top = 4.dp
            )
        )

        Column(
            modifier = Modifier.padding(
                top = 40.dp
            )
        ) {

            Text(
                text = "Favourites",
                color = Color.Gray,
                fontSize = 14.sp
            )

            Text(
                text = "Your favourite apps",
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(
                    top = 15.dp
                )
            )

            Text(
                text = "Drag the alphabet to browse apps",
                color = Color.Gray,
                fontSize = 14.sp,
                modifier = Modifier.padding(
                    top = 8.dp
                )
            )
        }
    }
}