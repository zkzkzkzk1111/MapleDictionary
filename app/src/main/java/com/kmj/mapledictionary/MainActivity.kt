package com.kmj.mapledictionary

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.kmj.mapledictionary.ui.theme.MapleDictionaryTheme
import com.kmj.presentation.screen.main.Main
import com.kmj.presentation.util.AppNavigation
import com.kmj.presentation.util.CustomImageLoader
import com.kmj.presentation.util.LocalCustomImageLoader
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var imageLoader: CustomImageLoader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CompositionLocalProvider(
                LocalCustomImageLoader provides imageLoader
            ) {
                AppNavigation()
            }

        }
    }

}

