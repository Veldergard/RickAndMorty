package ru.veldergard.rickandmorty.components.character

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ru.veldergard.network.models.domain.CharacterStatus
import ru.veldergard.rickandmorty.components.common.CharacterNameComponent

@Composable
fun CharacterDetailsNamePlateComponent(name: String, status: CharacterStatus) {
    Column(modifier = Modifier.fillMaxWidth()) {
        CharacterStatusComponent(characterStatus = status)
        CharacterNameComponent(name = name)
    }
}

@Preview
@Composable
fun NamePlatePreviewAlive() {
    CharacterDetailsNamePlateComponent(name = "Rick Sanchez", status = CharacterStatus.Alive)
}

@Preview
@Composable
fun NamePlatePreviewDead() {
    CharacterDetailsNamePlateComponent(name = "Rick Sanchez", status = CharacterStatus.Dead)
}

@Preview
@Composable
fun NamePlatePreviewUnknown() {
    CharacterDetailsNamePlateComponent(name = "Rick Sanchez", status = CharacterStatus.Unknown)
}