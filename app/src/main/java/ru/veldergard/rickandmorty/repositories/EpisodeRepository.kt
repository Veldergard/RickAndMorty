package ru.veldergard.rickandmorty.repositories

import ru.veldergard.network.ApiOperation
import ru.veldergard.network.KtorClient
import ru.veldergard.network.models.domain.Episode
import javax.inject.Inject

class EpisodeRepository @Inject constructor(
    private val ktorClient: KtorClient
) {
    suspend fun fetchEpisodes(episodeIds: List<Int>): ApiOperation<List<Episode>> {
        return ktorClient.getEpisodes(episodeIds)
    }
}