package ru.veldergard.rickandmorty.components.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import ru.veldergard.rickandmorty.ui.theme.RickAndMortyAction

@Composable
fun CharacterNameComponent(name: String) {
    Text(
        text = name,
        fontSize = 42.sp,
        lineHeight = 42.sp,
        fontWeight = FontWeight.Bold,
        color = RickAndMortyAction
    )
}

@Preview
@Composable
private fun CharacterNameComponentPreview() {
    CharacterNameComponent(name = "Rick Sanchez")
}