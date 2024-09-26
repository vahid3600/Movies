package com.vahid.tvshows.presentation.ui.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SectionHeader(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        text = title,
        modifier = modifier
            .padding(50.dp, 60.dp, 50.dp, 20.dp)
            .fillMaxWidth(),
        fontSize = 24.sp,
        textAlign = TextAlign.End
    )
}