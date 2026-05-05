package com.example.formulariotestuam.screen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.formulariotestuam.AppViewModel

@Composable
fun App(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val viewModel: AppViewModel = viewModel()

    // Observamos el estado del ViewModel
    val perfil by viewModel.perfil.collectAsState()
    val respuestas by viewModel.respuestas.collectAsState()
    val historial by viewModel.historial.collectAsState()
    val resultadoActual by viewModel.resultadoActual.collectAsState()

    NavHost(
        navController = navController,
        startDestination = Rutas.LOGIN,
        modifier = modifier
    ) {
        composable(Rutas.LOGIN) {
            LoginScreen(
                onIngresar = { nuevoPerfil ->
                    viewModel.iniciarSesion(nuevoPerfil)
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.INICIO) {
            HomeScreen(
                perfil = perfil,
                historial = historial,
                onIniciarTest = {
                    viewModel.limpiarRespuestas()
                    navController.navigate(Rutas.TEST)
                },
                onCerrarSesion = {
                    viewModel.cerrarSesion()
                    navController.navigate(Rutas.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }

        composable(Rutas.TEST) {
            TestVocacionalScreen(
                test = com.example.formulariotestuam.data.VocacionalRepository.testVocacional,
                respuestas = respuestas,
                onResponder = { indice, opcion ->
                    viewModel.responder(indice, opcion)
                },
                onVolver = {
                    navController.popBackStack()
                },
                onFinalizar = {
                    viewModel.calcularYGuardarResultado()
                    navController.navigate(Rutas.RESULTADO)
                }
            )
        }

        composable(Rutas.RESULTADO) {
            ResultadoScreen(
                perfil = perfil,
                resultado = resultadoActual,
                historial = historial,
                onRepetirTest = {
                    viewModel.limpiarRespuestas()
                    navController.navigate(Rutas.TEST) {
                        popUpTo(Rutas.RESULTADO) { inclusive = true }
                    }
                },
                onVolverInicio = {
                    navController.navigate(Rutas.INICIO) {
                        popUpTo(Rutas.RESULTADO) { inclusive = true }
                    }
                }
            )
        }
    }
}