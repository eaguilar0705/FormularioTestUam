package com.example.formulariotestuam.screen

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.formulariotestuam.model.PerfilEstudiante
import com.example.formulariotestuam.model.ResultadoVocacional
import com.example.formulariotestuam.ui.theme.UamAccent
import com.example.formulariotestuam.ui.theme.UamPrimary
import com.example.formulariotestuam.ui.theme.UamPrimaryDark
import com.example.formulariotestuam.ui.theme.UamTextSecondary
import com.example.formulariotestuam.ui.theme.White

@Composable
fun ResultadoScreen(
    perfil: PerfilEstudiante?,
    resultado: ResultadoVocacional?,
    historial: List<ResultadoVocacional>,
    onRepetirTest: () -> Unit,
    onVolverInicio: () -> Unit
) {
    if (resultado == null) {
        UamBackground {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("No hay resultado disponible.")
                Spacer(modifier = Modifier.height(12.dp))
                GradientButton(
                    text = "Volver al inicio",
                    onClick = onVolverInicio
                )
            }
        }
        return
    }

    val area = resultado.areaPrincipal
    val porcentaje = resultado.porcentajePrincipal()

    UamBackground {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            AnimatedVisibility(
                visible = true,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 3 })
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                    SectionTitle(
                        titulo = "Resultado vocacional",
                        subtitulo = "Estudiante: ${perfil?.nombre ?: "Sin nombre"}"
                    )

                    ModernCard {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(
                                modifier = Modifier
                                    .clip(CircleShape)
                                    .background(UamAccent)
                                    .padding(26.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = "$porcentaje%",
                                    color = UamPrimaryDark,
                                    style = MaterialTheme.typography.headlineMedium,
                                    fontWeight = FontWeight.Black
                                )
                            }

                            Spacer(modifier = Modifier.height(14.dp))

                            Text(
                                text = area.titulo,
                                color = UamPrimary,
                                style = MaterialTheme.typography.headlineSmall,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Text(
                                text = area.descripcion,
                                color = UamTextSecondary,
                                textAlign = TextAlign.Center
                            )

                            Spacer(modifier = Modifier.height(14.dp))

                            LinearProgressIndicator(
                                progress = porcentaje / 100f,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(10.dp),
                                color = UamPrimary,
                                trackColor = UamAccent
                            )
                        }
                    }

                    CardBloqueModerno(titulo = "Carreras sugeridas", emoji = "🎓") {
                        area.carreras.forEach { carrera ->
                            Text(
                                text = "• $carrera",
                                modifier = Modifier.padding(vertical = 3.dp)
                            )
                        }
                    }

                    CardBloqueModerno(titulo = "Recomendación", emoji = "💡") {
                        Text(
                            text = area.recomendacion,
                            color = UamTextSecondary
                        )
                    }

                    CardBloqueModerno(titulo = "Puntajes por área", emoji = "📊") {
                        resultado.puntajes.toList()
                            .sortedByDescending { it.second }
                            .forEach { item ->
                                val areaVocacional = item.first
                                val puntaje = item.second

                                Text(
                                    text = "${areaVocacional.titulo}: $puntaje puntos",
                                    fontWeight = if (areaVocacional == area) {
                                        FontWeight.Bold
                                    } else {
                                        FontWeight.Normal
                                    },
                                    modifier = Modifier.padding(vertical = 3.dp)
                                )
                            }
                    }

                    CardBloqueModerno(titulo = "Historial", emoji = "🕒") {
                        if (historial.isEmpty()) {
                            Text("Aún no hay resultados guardados.")
                        } else {
                            historial.takeLast(5).asReversed().forEachIndexed { index, item ->
                                Text(
                                    text = "${index + 1}. ${item.areaPrincipal.titulo} - ${item.porcentajePrincipal()}%",
                                    modifier = Modifier.padding(vertical = 3.dp)
                                )
                            }
                        }
                    }

                    Text(
                        text = "Este resultado es orientativo. Para una decisión profesional completa debe complementarse con acompañamiento docente o psicológico.",
                        textAlign = TextAlign.Center,
                        color = UamTextSecondary,
                        style = MaterialTheme.typography.bodySmall
                    )

                    GradientButton(
                        text = "Repetir test",
                        onClick = onRepetirTest
                    )

                    SoftButton(
                        text = "Volver al inicio",
                        onClick = onVolverInicio
                    )
                }
            }
        }
    }
}

@Composable
fun CardBloqueModerno(
    titulo: String,
    emoji: String,
    contenido: @Composable () -> Unit
) {
    ModernCard {
        Text(
            text = "$emoji  $titulo",
            color = UamPrimary,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(12.dp))

        contenido()
    }
}