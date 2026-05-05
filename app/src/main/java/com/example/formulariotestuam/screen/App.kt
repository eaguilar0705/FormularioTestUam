package com.example.formulariotestuam.screen

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.example.formulariotestuam.data.VocacionalRepository
import com.example.formulariotestuam.model.OpcionVocacional
import com.example.formulariotestuam.model.Pantalla
import com.example.formulariotestuam.model.PerfilEstudiante
import com.example.formulariotestuam.model.ResultadoVocacional

@Composable
fun App() {
    var pantallaActual by remember { mutableStateOf(Pantalla.LOGIN) }
    var perfil by remember { mutableStateOf<PerfilEstudiante?>(null) }
    var resultadoActual by remember { mutableStateOf<ResultadoVocacional?>(null) }

    val respuestas = remember { mutableStateMapOf<Int, OpcionVocacional>() }
    val historial = remember { mutableStateListOf<ResultadoVocacional>() }

    Crossfade(targetState = pantallaActual) { pantalla ->
        when (pantalla) {
            Pantalla.LOGIN -> {
                LoginScreen(
                    onIngresar = { nuevoPerfil ->
                        perfil = nuevoPerfil
                        pantallaActual = Pantalla.INICIO
                    }
                )
            }

            Pantalla.INICIO -> {
                HomeScreen(
                    perfil = perfil,
                    historial = historial,
                    onIniciarTest = {
                        respuestas.clear()
                        pantallaActual = Pantalla.TEST
                    },
                    onCerrarSesion = {
                        perfil = null
                        respuestas.clear()
                        resultadoActual = null
                        pantallaActual = Pantalla.LOGIN
                    }
                )
            }

            Pantalla.TEST -> {
                TestVocacionalScreen(
                    test = VocacionalRepository.testVocacional,
                    respuestas = respuestas,
                    onResponder = { indice, opcion ->
                        respuestas[indice] = opcion
                    },
                    onVolver = {
                        pantallaActual = Pantalla.INICIO
                    },
                    onFinalizar = {
                        val resultado = VocacionalRepository.testVocacional.calcularResultado(respuestas.values)
                        resultadoActual = resultado
                        historial.add(resultado)
                        pantallaActual = Pantalla.RESULTADO
                    }
                )
            }

            Pantalla.RESULTADO -> {
                ResultadoScreen(
                    perfil = perfil,
                    resultado = resultadoActual,
                    historial = historial,
                    onRepetirTest = {
                        respuestas.clear()
                        pantallaActual = Pantalla.TEST
                    },
                    onVolverInicio = {
                        pantallaActual = Pantalla.INICIO
                    }
                )
            }
        }
    }
}