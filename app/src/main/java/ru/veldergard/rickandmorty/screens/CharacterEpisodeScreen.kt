package ru.veldergard.rickandmorty.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.veldergard.network.models.domain.Character
import ru.veldergard.network.models.domain.Episode
import ru.veldergard.rickandmorty.components.common.CharacterImage
import ru.veldergard.rickandmorty.components.common.CharacterNameComponent
import ru.veldergard.rickandmorty.components.common.DataPoint
import ru.veldergard.rickandmorty.components.common.DataPointComponent
import ru.veldergard.rickandmorty.components.common.LoadingState
import ru.veldergard.rickandmorty.components.episode.EpisodeRowComponent
import ru.veldergard.rickandmorty.ui.theme.RickAndMortyPrimary
import ru.veldergard.rickandmorty.ui.theme.RickAndMortyTextPrimary
import ru.veldergard.rickandmorty.viewmodels.CharacterEpisodeViewModel

sealed interface CharacterEpisodeViewState {
    data object Loading : CharacterEpisodeViewState

    data class Error(
        val message: String
    ) : CharacterEpisodeViewState

    data class Success(
        val character: Character,
        val characterEpisodes: List<Episode>
    ) : CharacterEpisodeViewState
}

@Composable
fun CharacterEpisodeScreen(
    characterId: Int,
    viewModel: CharacterEpisodeViewModel = hiltViewModel()
) {
    LaunchedEffect(key1 = Unit, block = {
        viewModel.fetchEpisodes(characterId)
    })

    val state by viewModel.stateFlow.collectAsState()

    when (val viewState = state) {
        CharacterEpisodeViewState.Loading -> {
            LoadingState()
        }

        is CharacterEpisodeViewState.Error -> {
            // TODO
        }

        is CharacterEpisodeViewState.Success -> {
            MainScreen(
                character = viewState.character,
                episodes =  viewState.characterEpisodes
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainScreen(character: Character, episodes: List<Episode>) {
    val episodeBySeasonMap = episodes.groupBy { it.seasonNumber }

    LazyColumn(contentPadding = PaddingValues(all = 16.dp)) {
        item { CharacterNameComponent(name = character.name) }
        item { Spacer(modifier = Modifier.height(8.dp)) }
        item {
            LazyRow {
                episodeBySeasonMap.forEach { mapEntry ->
                    val title = "Season ${mapEntry.key}"
                    val description = "${mapEntry.value.size} ep"
                    item {
                        DataPointComponent(dataPoint = DataPoint(title, description))
                        Spacer(modifier = Modifier.width(32.dp))
                    }
                }
            }
        }
        item { Spacer(modifier = Modifier.height(16.dp)) }
        item { CharacterImage(imageUrl = character.imageUrl) }
        item { Spacer(modifier = Modifier.height(32.dp)) }

        episodeBySeasonMap.forEach { mapEntry ->
            stickyHeader { SeasonHeader(seasonNumber = mapEntry.key) }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            items(mapEntry.value) { episode ->
                EpisodeRowComponent(episode = episode)
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Composable
private fun SeasonHeader(seasonNumber: Int) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = RickAndMortyPrimary)
            .padding(top = 8.dp, bottom = 16.dp)
    ) {
        Text(
            text = "Season $seasonNumber",
            color = RickAndMortyTextPrimary,
            fontSize = 32.sp,
            lineHeight = 32.sp,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = RickAndMortyTextPrimary,
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(vertical = 4.dp)
        )
    }
}