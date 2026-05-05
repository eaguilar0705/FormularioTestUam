package com.example.formulariotestuam

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.formulariotestuam.screen.App
import com.example.formulariotestuam.ui.theme.FormularioTestUamTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            FormularioTestUamTheme {
                App()
            }
        }
    }
}