package com.example.edyoupicturegallery

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.edyoupicturegallery.ui.theme.EDYOUPictureGalleryTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            EDYOUPictureGalleryTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Set up navigation
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "splash") {
                        composable("splash") {
                            SplashScreen(onTimeout = {
                                navController.navigate("main")
                            })
                        }
                        composable("main") {
                            MainScreen(imageResourceIds = listOf(
                                R.drawable.my_image,
                                R.drawable.another_image
                                // Add more image resource IDs as needed
                            ))
                        }
                    }
                }
            }
        }
    }

    @Composable
    fun SplashScreen(onTimeout: () -> Unit) {
        var visible by remember { mutableStateOf(true) }

        LaunchedEffect(Unit) {
            // Simulate a delay for the splash screen
            delay(2000L)
            visible = false
            onTimeout()
        }

        if (visible) {
            // Set the background color using HEX code
            Surface(
                color = Color(android.graphics.Color.parseColor("#002b45"))
            ) {
                // Display your splash screen content here
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.euihr),
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            }
        }
    }


    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun MainScreen(imageResourceIds: List<Int>) {
        // Your MainScreen content here
        val pagerState = rememberPagerState(
            initialPage = 0,
            pageCount = { imageResourceIds.size },
            initialPageOffsetFraction = 0f // or the desired initial offset fraction
        )

        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            ImagePager(
                pagerState = pagerState,
                imageResourceIds = imageResourceIds
            )
        }
    }
    @OptIn(ExperimentalFoundationApi::class)
    @Composable
    fun ImagePager(pagerState: PagerState, imageResourceIds: List<Int>) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { page ->
            Image(
                painter = painterResource(id = imageResourceIds[page]),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .graphicsLayer(
                        translationX = calculateTranslation(page, pagerState.currentPage)
                    )
            )
        }
    }

    private fun calculateTranslation(page: Int, currentPage: Int): Float {
        val offset = page - currentPage
        return (-offset * 1000).toFloat() // Adjust this multiplier for smoother or faster animations
    }
}