package com.example.briefly.ui.screens // <--- FIXED: Added correct package path

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.briefly.domain.util.Resource
import com.example.briefly.ui.Screen
import com.example.briefly.ui.components.NewsCard
import com.example.briefly.ui.viewmodel.HomeViewModel
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val state by viewModel.newsState.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        when (val result = state) {
            is Resource.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is Resource.Success -> {
                LazyColumn {
                    items(result.data ?: emptyList()) { article ->
                        NewsCard(
                            article = article,
                            // FIXED: Changed 'onClick' to 'onItemClick' to match your component
                            onItemClick = {
                                // 1. Get the title safely
                                val title = article.title ?: "Unknown"

                                // 2. Encode it (Titles have spaces/symbols that break URLs)
                                val encodedTitle = URLEncoder.encode(title, StandardCharsets.UTF_8.toString())

                                // 3. Navigate
                                navController.navigate(Screen.Detail.withArgs(encodedTitle))
                            }
                        )
                    }
                }
            }

            is Resource.Error -> {
                Text(
                    text = result.message ?: "Unknown Error",
                    color = Color.Red,
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}