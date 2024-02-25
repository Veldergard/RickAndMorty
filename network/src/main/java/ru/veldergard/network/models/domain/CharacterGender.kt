package ru.veldergard.network.models.domain

sealed class CharacterGender(val displayName: String) {
    data object Male : CharacterGender("Male")
    data object Female : CharacterGender("Female")
    data object Genderless : CharacterGender("No gender")
    data object Unknown : CharacterGender("Not specified")
}