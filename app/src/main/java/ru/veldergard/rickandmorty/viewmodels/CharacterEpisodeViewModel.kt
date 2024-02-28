package ru.veldergard.rickandmorty.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.veldergard.rickandmorty.repositories.CharacterRepository
import ru.veldergard.rickandmorty.repositories.EpisodeRepository
import ru.veldergard.rickandmorty.screens.CharacterEpisodeViewState

@HiltViewModel
class CharacterEpisodeViewModel(
    private val characterRepository: CharacterRepository,
    private val episodeRepository: EpisodeRepository
) : ViewModel() {
    private val _internalStorageFlow = MutableStateFlow<CharacterEpisodeViewState>(
        value = CharacterEpisodeViewState.Loading
    )
    val stateFlow = _internalStorageFlow.asStateFlow()

    fun fetchEpisodes(characterId: Int) = viewModelScope.launch {
        _internalStorageFlow.update {
            return@update CharacterEpisodeViewState.Loading
        }

        characterRepository.fetchCharacter(characterId).onSuccess { character ->
            launch {
                episodeRepository.fetchEpisodes(character.episodeIds).onSuccess { characterEpisodes ->
                    _internalStorageFlow.update {
                        return@update CharacterEpisodeViewState.Success(
                            character = character,
                            characterEpisodes = characterEpisodes
                        )
                    }
                }.onFailure { exception ->
                    _internalStorageFlow.update {
                        CharacterEpisodeViewState.Error(message = exception.message ?: "Unknown error occurred")
                    }
                }
            }
        }.onFailure { exception ->
            _internalStorageFlow.update {
                CharacterEpisodeViewState.Error(message = exception.message ?: "Unknown error occurred")
            }
        }
    }
}