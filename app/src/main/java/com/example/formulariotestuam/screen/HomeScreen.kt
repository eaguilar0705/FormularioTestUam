package com.example.formulariotestuam.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.formulariotestuam.data.VocacionalRepository
import com.example.formulariotestuam.model.PerfilEstudiante
import com.example.formulariotestuam.model.ResultadoVocacional
import com.example.formulariotestuam.ui.theme.UamPrimary
import com.example.formulariotestuam.ui.theme.UamTextSecondary

@Composable
fun HomeScreen(
    perfil: PerfilEstudiante?,
    historial: List<ResultadoVocacional>,
    onIniciarTest: () -> Unit,
    onCerrarSesion: () -> Unit
) {
    val ultimoResultado = historial.lastOrNull()

    UamBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 3 })
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    SectionTitle(
                        titulo = "Hola, ${perfil?.primerNombre() ?: "estudiante"}",
                        subtitulo = "Bienvenido al módulo de orientación vocacional UAM"
                    )

                    ModernCard {
                        InfoPill(text = "Usuario identificado")

                        Spacer(modifier = Modifier.height(14.dp))

                        Text(
                            text = perfil?.nombre ?: "Estudiante",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = UamPrimary
                        )

                        Text(
                            text = "Colegio: ${perfil?.colegio ?: "No registrado"}",
                            color = UamTextSecondary
                        )

                        Text(
                            text = "Correo: ${perfil?.correo ?: "No registrado"}",
                            color = UamTextSecondary
                        )
                    }

                    ModernCard {
                        Text(
                            text = "Objetivo del proyecto",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Apoyar la orientación profesional de estudiantes que egresan de secundaria y aspiran a ingresar a la universidad.",
                            color = UamTextSecondary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        InfoPill(text = "Tema elegido: Test Vocacional")
                    }

                    ModernCard {
                        Text(
                            text = "Módulos propuestos",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = UamPrimary
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        VocacionalRepository.modulosPropuestos.forEachIndexed { index, modulo ->
                            Text(
                                text = "${index + 1}. $modulo",
                                modifier = Modifier.padding(vertical = 5.dp),
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        StatCard(
                            titulo = "Tests hechos",
                            valor = historial.size.toString(),
                            emoji = "📘",
                            modifier = Modifier.weight(1f)
                        )

                        StatCard(
                            titulo = "Última área",
                            valor = ultimoResultado?.areaPrincipal?.titulo ?: "Sin resultado",
                            emoji = "🎯",
                            modifier = Modifier.weight(1f)
                        )
                    }

                    GradientButton(
                        text = "Comenzar test vocacional",
                        onClick = onIniciarTest
                    )

                    SoftButton(
                        text = "Cerrar sesión",
                        onClick = onCerrarSesion
                    )
                }
            }
        }
    }
}