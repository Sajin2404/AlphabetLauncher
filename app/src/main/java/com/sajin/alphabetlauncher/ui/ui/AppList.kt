package com.sajin.alphabetlauncher.ui

import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import com.sajin.alphabetlauncher.data.AppInfo

@Composable
fun AppList(
    apps: List<AppInfo>,
    onAppClick: (AppInfo) -> Unit
) {

    if (apps.isEmpty()) {

        Text(
            text = "No apps",
            fontSize = 20.sp,
            modifier = Modifier.padding(24.dp)
        )

        return
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 24.dp,
                end = 80.dp
            ),
        verticalArrangement =
            Arrangement.spacedBy(8.dp)
    ) {

        items(
            items = apps,
            key = { it.packageName }
        ) { app ->

            AppRow(
                app = app,
                onClick = {
                    onAppClick(app)
                }
            )
        }
    }
}

@Composable
private fun AppRow(
    app: AppInfo,
    onClick: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp)
            .clickable {
                onClick()
            }
            .padding(horizontal = 8.dp),

        verticalAlignment =
            Alignment.CenterVertically,

        horizontalArrangement =
            Arrangement.Start
    ) {

        Image(
            bitmap = app.icon.toImageBitmap(),
            contentDescription = app.name,
            modifier = Modifier.size(44.dp)
        )

        Spacer(
            modifier = Modifier.size(16.dp)
        )

        Text(
            text = app.name,
            fontSize = 18.sp
        )
    }
}

private fun Drawable.toImageBitmap(): ImageBitmap {

    return toBitmap(
        width = 96,
        height = 96
    ).asImageBitmap()
}