package ru.veldergard.rickandmorty.components.common

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.veldergard.rickandmorty.ui.theme.RickAndMortyAction
import ru.veldergard.rickandmorty.ui.theme.RickAndMortyTextPrimary

data class DataPoint(
    val title: String,
    val description: String
)

@Composable
fun DataPointComponent(dataPoint: DataPoint) {
    Column {
        Text(
            text = dataPoint.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold,
            color = RickAndMortyAction
        )
        Text(
            text = dataPoint.description,
            fontSize = 24.sp,
            color = RickAndMortyTextPrimary
        )
    }
}

@Preview
@Composable
fun DataPointComponentPreview() {
    val data = DataPoint(title = "Last known location", description = "Citadel of Ricks")
    DataPointComponent(dataPoint = data)
}