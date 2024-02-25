package ru.veldergard.rickandmorty

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.delay
import ru.veldergard.network.KtorClient
import ru.veldergard.network.models.domain.Character
import ru.veldergard.rickandmorty.screens.CharacterDetailsScreen
import ru.veldergard.rickandmorty.ui.theme.RickAndMortyPrimary
import ru.veldergard.rickandmorty.ui.theme.RickAndMortyTheme

class MainActivity : ComponentActivity() {

    private val ktorClient = KtorClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
//            var character by remember {
//                mutableStateOf<Character?>(null)
//            }
//            val scope = rememberCoroutineScope()
//
//            LaunchedEffect(key1 = Unit, block = {
//                delay(5000)
//                character = ktorClient.getCharacter(1)
//            })

            RickAndMortyTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = RickAndMortyPrimary
                ) {
                    CharacterDetailsScreen(ktorClient = ktorClient, characterId = 1)
                }
//                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
//                    Surface(
//                        modifier = Modifier.padding(innerPadding),
//                        color = MaterialTheme.colorScheme.background
//                    ) {
//                        Column {
//                            Greeting(
//                                name = "Android",
//                                modifier = Modifier.padding(innerPadding)
//                            )
//                            Text(
//                                text = character?.name ?: "No character",
//                                modifier = Modifier.padding(innerPadding)
//                            )
//                        }
//                    }
//                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    RickAndMortyTheme {
        Greeting("Android")
    }
}