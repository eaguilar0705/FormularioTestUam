package com.example.formulariotestuam

import androidx.lifecycle.ViewModel
import com.example.formulariotestuam.data.VocacionalRepository
import com.example.formulariotestuam.model.OpcionVocacional
import com.example.formulariotestuam.model.PerfilEstudiante
import com.example.formulariotestuam.model.ResultadoVocacional
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AppViewModel : ViewModel() {
    private val _perfil = MutableStateFlow<PerfilEstudiante?>(null)
    val perfil: StateFlow<PerfilEstudiante?> = _perfil.asStateFlow()
    private val _respuestas = MutableStateFlow<Map<Int, OpcionVocacional>>(emptyMap())
    val respuestas: StateFlow<Map<Int, OpcionVocacional>> = _respuestas.asStateFlow()
    private val _resultadoActual = MutableStateFlow<ResultadoVocacional?>(null)
    val resultadoActual: StateFlow<ResultadoVocacional?> = _resultadoActual.asStateFlow()
    private val _historial = MutableStateFlow<List<ResultadoVocacional>>(emptyList())
    val historial: StateFlow<List<ResultadoVocacional>> = _historial.asStateFlow()

    fun iniciarSesion(nuevoPerfil: PerfilEstudiante) {
        _perfil.update { nuevoPerfil }
    }

    fun cerrarSesion() {
        _perfil.update { null }
        _respuestas.update { emptyMap() }
        _resultadoActual.update { null }
    }

    fun responder(indice: Int, opcion: OpcionVocacional) {
        _respuestas.update { actual ->
            actual + (indice to opcion)
        }
    }

    fun limpiarRespuestas() {
        _respuestas.update { emptyMap() }
    }

    fun calcularYGuardarResultado() {
        val resultado = VocacionalRepository.testVocacional
            .calcularResultado(_respuestas.value.values)
        _resultadoActual.update { resultado }
        _historial.update { it + resultado }
    }
}