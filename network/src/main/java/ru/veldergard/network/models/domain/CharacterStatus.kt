package ru.veldergard.network.models.domain

import androidx.compose.ui.graphics.Color


sealed class CharacterStatus(val displayName: String, val color: Color) {
    data object Alive: CharacterStatus("Alive", Color.Green)
    data object Dead: CharacterStatus("Dead", Color.Red)
    data object Unknown: CharacterStatus("Unknown", Color.Yellow)
}