package ru.veldergard.rickandmorty.repositories

import ru.veldergard.network.ApiOperation
import ru.veldergard.network.KtorClient
import ru.veldergard.network.models.domain.Character
import javax.inject.Inject

class CharacterRepository @Inject constructor(
    private val ktorClient: KtorClient
) {
    suspend fun fetchCharacter(characterId: Int): ApiOperation<Character> {
        return ktorClient.getCharacter(characterId)
    }
}