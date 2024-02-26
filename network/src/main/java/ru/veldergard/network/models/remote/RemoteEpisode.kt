package ru.veldergard.network.models.remote

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import ru.veldergard.network.models.domain.Episode

@Serializable
data class RemoteEpisode(
    val id: Int,
    val name: String,
    val episode: String, // S03E07
    @SerialName("air_date") val airDate: String,
    val characters: List<String> // Character Urls
)

fun RemoteEpisode.toDomainEpisode(): Episode {
    return Episode(
        id = id,
        name = name,
        seasonNumber = episode.filter { it.isDigit() }.take(2).toInt(),
        episodeNumber = episode.filter { it.isDigit() }.takeLast(2).toInt(),
        airDate = airDate,
        characterIdsInEpisode = characters.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1).toInt()
        }
    )
}