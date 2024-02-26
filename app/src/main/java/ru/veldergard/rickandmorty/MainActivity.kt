package ru.veldergard.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.delay
import ru.veldergard.network.KtorClient
import ru.veldergard.network.models.domain.Character
import ru.veldergard.rickandmorty.screens.CharacterDetailsScreen
import ru.veldergard.rickandmorty.screens.CharacterEpisodeScreen
import ru.veldergard.rickandmorty.ui.theme.RickAndMortyPrimary
import ru.veldergard.rickandmorty.ui.theme.RickAndMortyTheme

class MainActivity : ComponentActivity() {

    private val ktorClient = KtorClient()

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
                                    ktorClient = ktorClient,
                                    characterId = 1
                                ) {
                                    navController.navigate("character_episodes/$it")
                                }
                            }

                            composable(
                                route = "character_episodes/{characterId}",
                                arguments = listOf(navArgument("characterId") { type = NavType.IntType })
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
