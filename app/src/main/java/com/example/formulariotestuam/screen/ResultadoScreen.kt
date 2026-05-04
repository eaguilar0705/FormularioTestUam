package com.example.formulariotestuam.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.formulariotestuam.model.PerfilEstudiante
import com.example.formulariotestuam.model.ResultadoVocacional

@Composable
fun ResultadoScreen(
    perfil: PerfilEstudiante?,
    resultado: ResultadoVocacional?,
    historial: List<ResultadoVocacional>,
    onRepetirTest: () -> Unit,
    onVolverInicio: () -> Unit
) {
    if (resultado == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("No hay resultado disponible.")

            Spacer(modifier = Modifier.height(12.dp))

            Button(onClick = onVolverInicio) {
                Text("Volver al inicio")
            }
        }

        return
    }

    val area = resultado.areaPrincipal

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Resultado vocacional",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(text = "Estudiante: ${perfil?.nombre ?: "Sin nombre"}")

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(26.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.10f)
            )
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    text = "Área principal",
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = area.titulo,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Text(text = area.descripcion)

                Text(
                    text = "Compatibilidad aproximada: ${resultado.porcentajePrincipal()}%",
                    fontWeight = FontWeight.Bold
                )

                LinearProgressIndicator(
                    progress = resultado.porcentajePrincipal() / 100f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Carreras sugeridas",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                area.carreras.forEach { carrera ->
                    Text(text = "• $carrera")
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Recomendación",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                Text(text = area.recomendacion)
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Puntajes por área",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

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
                            }
                        )
                    }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(22.dp),
            elevation = CardDefaults.cardElevation(3.dp)
        ) {
            Column(
                modifier = Modifier.padding(18.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Historial de resultados",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                if (historial.isEmpty()) {
                    Text("Aún no hay resultados guardados.")
                } else {
                    historial.takeLast(5).asReversed().forEachIndexed { index, item ->
                        Text(
                            text = "${index + 1}. ${item.areaPrincipal.titulo} - ${item.porcentajePrincipal()}%"
                        )
                    }
                }
            }
        }

        Text(
            text = "Importante: este resultado es orientativo. Para una decisión profesional completa, debe complementarse con acompañamiento docente o psicológico.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodySmall
        )

        Button(
            onClick = onRepetirTest,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Repetir test")
        }

        OutlinedButton(
            onClick = onVolverInicio,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Volver al inicio")
        }
    }
}