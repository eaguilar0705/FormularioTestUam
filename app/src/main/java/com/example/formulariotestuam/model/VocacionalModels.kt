package com.example.formulariotestuam.model

enum class Pantalla {
    LOGIN,
    INICIO,
    TEST,
    RESULTADO
}

enum class AreaVocacional(
    val titulo: String,
    val descripcion: String,
    val carreras: List<String>,
    val recomendacion: String
) {
    TECNOLOGIA(
        titulo = "Tecnología e Ingeniería",
        descripcion = "Te interesa resolver problemas, crear sistemas, usar herramientas digitales y construir soluciones.",
        carreras = listOf(
            "Ingeniería en Sistemas",
            "Ingeniería Industrial",
            "Análisis de Datos",
            "Desarrollo de Software"
        ),
        recomendacion = "Explora programación, bases de datos, lógica, diseño de aplicaciones y proyectos tecnológicos."
    ),

    SALUD(
        titulo = "Salud y Bienestar",
        descripcion = "Te interesa cuidar, escuchar, orientar o apoyar el bienestar físico y emocional de las personas.",
        carreras = listOf(
            "Psicología",
            "Medicina",
            "Enfermería",
            "Fisioterapia",
            "Nutrición"
        ),
        recomendacion = "Participa en charlas de salud, bienestar estudiantil, primeros auxilios u orientación humana."
    ),

    SOCIAL(
        titulo = "Social, Educación y Humanidades",
        descripcion = "Te gusta comunicar, enseñar, acompañar, comprender problemas sociales y trabajar con personas.",
        carreras = listOf(
            "Psicología",
            "Educación",
            "Trabajo Social",
            "Derecho",
            "Comunicación"
        ),
        recomendacion = "Prueba actividades de tutoría, liderazgo, debate, voluntariado o acompañamiento estudiantil."
    ),

    ADMINISTRACION(
        titulo = "Administración y Negocios",
        descripcion = "Te atrae organizar recursos, dirigir equipos, vender ideas, planificar metas y tomar decisiones.",
        carreras = listOf(
            "Administración de Empresas",
            "Mercadeo",
            "Contabilidad",
            "Finanzas",
            "Emprendimiento"
        ),
        recomendacion = "Explora ferias de emprendimiento, simulaciones de negocio, ventas, liderazgo y organización de eventos."
    ),

    CREATIVIDAD(
        titulo = "Arte, Diseño y Comunicación",
        descripcion = "Te gusta crear, diseñar, comunicar ideas visuales, escribir, producir contenido o imaginar soluciones originales.",
        carreras = listOf(
            "Diseño Gráfico",
            "Publicidad",
            "Comunicación",
            "Arquitectura",
            "Producción Audiovisual"
        ),
        recomendacion = "Crea un portafolio con diseños, videos, dibujos, fotografías, textos o ideas de campañas."
    ),

    CIENCIAS(
        titulo = "Ciencias e Investigación",
        descripcion = "Te interesa observar, experimentar, analizar datos, comparar resultados y buscar explicaciones con evidencia.",
        carreras = listOf(
            "Biología",
            "Química",
            "Matemática",
            "Estadística",
            "Investigación Científica"
        ),
        recomendacion = "Participa en ferias científicas, laboratorios, clubes de ciencia o proyectos de investigación."
    )
}

data class PerfilEstudiante(
    val nombre: String,
    val correo: String,
    val colegio: String
) {
    fun primerNombre(): String {
        val partes = nombre.trim().split(" ")
        return partes.firstOrNull() ?: "Estudiante"
    }
}

data class OpcionVocacional(
    val texto: String,
    val area: AreaVocacional,
    val puntaje: Int = 3
)

data class PreguntaVocacional(
    val enunciado: String,
    val opciones: List<OpcionVocacional>
)

data class ResultadoVocacional(
    val areaPrincipal: AreaVocacional,
    val puntajes: Map<AreaVocacional, Int>,
    val puntajeMaximo: Int
) {
    fun porcentajePrincipal(): Int {
        val puntaje = puntajes[areaPrincipal] ?: 0
        val porcentaje = (puntaje.toDouble() / puntajeMaximo.toDouble()) * 100
        return porcentaje.toInt().coerceIn(0, 100)
    }
}

class TestVocacional(
    val titulo: String,
    val modulo: String,
    val descripcion: String,
    val preguntas: List<PreguntaVocacional>
) {
    fun calcularResultado(respuestas: Collection<OpcionVocacional>): ResultadoVocacional {
        val puntajes = mutableMapOf<AreaVocacional, Int>()

        AreaVocacional.entries.forEach { area ->
            puntajes[area] = 0
        }

        respuestas.forEach { opcion ->
            val acumulado = puntajes[opcion.area] ?: 0
            puntajes[opcion.area] = acumulado + opcion.puntaje
        }

        val areaGanadora = puntajes.maxByOrNull { item ->
            item.value
        }?.key ?: AreaVocacional.TECNOLOGIA

        val maximo = preguntas.size * 3

        return ResultadoVocacional(
            areaPrincipal = areaGanadora,
            puntajes = puntajes,
            puntajeMaximo = maximo
        )
    }
}