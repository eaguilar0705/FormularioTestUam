package com.example.formulariotestuam.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.formulariotestuam.model.OpcionVocacional
import com.example.formulariotestuam.model.PreguntaVocacional
import com.example.formulariotestuam.model.TestVocacional

@Composable
fun TestVocacionalScreen(
    test: TestVocacional,
    respuestas: Map<Int, OpcionVocacional>,
    onResponder: (Int, OpcionVocacional) -> Unit,
    onVolver: () -> Unit,
    onFinalizar: () -> Unit
) {
    val totalPreguntas = test.preguntas.size
    val respondidas = respuestas.size
    val progreso = respondidas.toFloat() / totalPreguntas.toFloat()
    val puedeFinalizar = respondidas == totalPreguntas

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(18.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            text = test.titulo,
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )

        Text(
            text = test.modulo,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.secondary
        )

        Text(text = "Progreso: $respondidas de $totalPreguntas preguntas")

        LinearProgressIndicator(
            progress = progreso,
            modifier = Modifier.fillMaxWidth()
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            itemsIndexed(test.preguntas) { index, pregunta ->
                PreguntaCard(
                    numero = index + 1,
                    pregunta = pregunta,
                    respuestaSeleccionada = respuestas[index],
                    onSeleccionar = { opcion ->
                        onResponder(index, opcion)
                    }
                )
            }
        }

        Button(
            onClick = onFinalizar,
            enabled = puedeFinalizar,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Ver resultado vocacional")
        }

        OutlinedButton(
            onClick = onVolver,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(16.dp)
        ) {
            Text("Volver al inicio")
        }
    }
}

@Composable
fun PreguntaCard(
    numero: Int,
    pregunta: PreguntaVocacional,
    respuestaSeleccionada: OpcionVocacional?,
    onSeleccionar: (OpcionVocacional) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(22.dp),
        elevation = CardDefaults.cardElevation(3.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Pregunta $numero",
                color = MaterialTheme.colorScheme.primary,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = pregunta.enunciado,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            pregunta.opciones.forEach { opcion ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onSeleccionar(opcion)
                        }
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = respuestaSeleccionada == opcion,
                        onClick = {
                            onSeleccionar(opcion)
                        }
                    )

                    Text(
                        text = opcion.texto,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}