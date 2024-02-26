package ru.veldergard.rickandmorty.components.episode

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.veldergard.network.models.domain.Episode
import ru.veldergard.rickandmorty.components.common.DataPoint
import ru.veldergard.rickandmorty.components.common.DataPointComponent
import ru.veldergard.rickandmorty.ui.theme.RickAndMortyTextPrimary

@Composable
fun EpisodeRowComponent(episode: Episode) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        DataPointComponent(
            dataPoint = DataPoint(
                title = "Episode",
                description = episode.episodeNumber.toString()
            )
        )
        Spacer(modifier = Modifier.width(64.dp))
        Column {
            Text(
                text = episode.name,
                fontSize = 24.sp,
                color = RickAndMortyTextPrimary,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
            Text(
                text = episode.airDate,
                fontSize = 16.sp,
                color = RickAndMortyTextPrimary,
                fontStyle = FontStyle.Italic,
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }
}