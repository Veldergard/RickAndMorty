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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import kotlinx.coroutines.delay
import ru.veldergard.network.ApiOperation
import ru.veldergard.network.KtorClient
import ru.veldergard.network.models.domain.Character
import ru.veldergard.rickandmorty.components.character.CharacterDetailsNamePlateComponent
import ru.veldergard.rickandmorty.components.common.DataPoint
import ru.veldergard.rickandmorty.components.common.DataPointComponent
import ru.veldergard.rickandmorty.components.common.LoadingState
import ru.veldergard.rickandmorty.ui.theme.RickAndMortyAction

@Composable
fun CharacterDetailsScreen(
    ktorClient: KtorClient,
    characterId: Int,
    onEpisodeClicked: (Int) -> Unit
) {
    var character by remember { mutableStateOf<Character?>(null) }

    val characterDataPoints: List<DataPoint> by remember {
        derivedStateOf {
            buildList {
                character?.let { character ->
                    add(DataPoint("Last known location", character.location.name))
                    add(DataPoint("Species", character.species))
                    add(DataPoint("Gender", character.gender.displayName))
                    character.type.takeIf { it.isNotEmpty() }?.let { type ->
                        add(DataPoint("Type", type))
                    }
                    add(DataPoint("Origin", character.origin.name))
                    add(DataPoint("Episode count", character.episodeUrls.size.toString()))
                }
            }
        }
    }

    LaunchedEffect(key1 = Unit, block = {
        ktorClient
            .getCharacter(characterId)
            .onSuccess {
                character = it
            }.onFailure {
                // todo handle exception
            }
    })

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(all = 16.dp)
    ) {
        if (character == null) {
            item { LoadingState() }
            return@LazyColumn
        }

        // Name plate
        item {
            CharacterDetailsNamePlateComponent(
                name = character!!.name,
                status = character!!.status
            )
        }

        item { Spacer(modifier = Modifier.height(8.dp)) }

        // Image
        item {
            SubcomposeAsyncImage(
                model = character!!.imageUrl,
                contentDescription = "Character image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(12.dp)),
                loading = { LoadingState() }
            )
        }

        // Data points
        items(characterDataPoints) {
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