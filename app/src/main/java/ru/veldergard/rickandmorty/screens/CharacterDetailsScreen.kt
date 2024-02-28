package ru.veldergard.rickandmorty.screens

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.veldergard.network.models.domain.Character
import ru.veldergard.rickandmorty.components.character.CharacterDetailsNamePlateComponent
import ru.veldergard.rickandmorty.components.common.CharacterImage
import ru.veldergard.rickandmorty.components.common.DataPoint
import ru.veldergard.rickandmorty.components.common.DataPointComponent
import ru.veldergard.rickandmorty.components.common.LoadingState
import ru.veldergard.rickandmorty.ui.theme.RickAndMortyAction
import ru.veldergard.rickandmorty.viewmodels.CharacterDetailsViewModel

sealed interface CharacterDetailsViewState {
    data object Loading : CharacterDetailsViewState

    data class Error(
        val message: String
    ) : CharacterDetailsViewState

    data class Success(
        val character: Character,
        val characterDataPoints: List<DataPoint>
    ) : CharacterDetailsViewState
}

@Composable
fun CharacterDetailsScreen(
    characterId: Int,
    viewModel: CharacterDetailsViewModel = hiltViewModel(),
    onEpisodeClicked: (Int) -> Unit
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchCharacter(characterId)
    })

    val state by viewModel.stateFlow.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 16.dp)
    ) {
        when (val viewState = state) {
            CharacterDetailsViewState.Loading -> {
                item { LoadingState() }
            }

            is CharacterDetailsViewState.Error -> {
                // TODO
            }

            is CharacterDetailsViewState.Success -> {
                // Name
                item {
                    CharacterDetailsNamePlateComponent(
                        name = viewState.character.name,
                        status = viewState.character.status
                    )
                }
                item { Spacer(modifier = Modifier.height(8.dp)) }

                // Image
                item { CharacterImage(imageUrl = viewState.character.imageUrl) }

                // Data points
                items(viewState.characterDataPoints) {
                    Spacer(modifier = Modifier.height(32.dp))
                    DataPointComponent(dataPoint = it)
                }
                item { Spacer(modifier = Modifier.height(32.dp)) }

                // Button
                item {
                    Text(
                        text = "View all episodes",
                        color = RickAndMortyAction,
                        fontSize = 18.sp,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .padding(horizontal = 32.dp)
                            .border(
                                width = 1.dp,
                                color = RickAndMortyAction,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .clip(RoundedCornerShape(12.dp))
                            .clickable {
                                onEpisodeClicked(characterId)
                            }
                            .padding(vertical = 8.dp)
                            .fillMaxWidth()
                    )
                }

                item { Spacer(modifier = Modifier.height(64.dp)) }
            }
        }
    }
}