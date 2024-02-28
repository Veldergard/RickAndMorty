package ru.veldergard.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import dagger.hilt.android.AndroidEntryPoint
import ru.veldergard.network.KtorClient
import ru.veldergard.rickandmorty.screens.CharacterDetailsScreen
import ru.veldergard.rickandmorty.screens.CharacterEpisodeScreen
import ru.veldergard.rickandmorty.ui.theme.RickAndMortyPrimary
import ru.veldergard.rickandmorty.ui.theme.RickAndMortyTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var ktorClient: KtorClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()

            RickAndMortyTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = RickAndMortyPrimary
                    ) {
                        NavHost(navController = navController, startDestination = "character_details") {
                            composable("character_details") {
                                CharacterDetailsScreen(
                                    characterId = 1
                                ) {
                                    navController.navigate("character_episodes/$it")
                                }
                            }

                            composable(
                                route = "character_episodes/{characterId}",
                                arguments = listOf(navArgument("characterId") {
                                    type = NavType.IntType
                                })
                            ) { backStackEntry ->
                                val characterId: Int = backStackEntry.arguments?.getInt("characterId") ?: 0
                                CharacterEpisodeScreen(
                                    characterId = characterId,
                                    ktorClient = ktorClient
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
