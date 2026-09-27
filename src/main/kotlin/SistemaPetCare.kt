import kotlinx.coroutines.delay

class SistemaPetCare {
    private val boxes: List<Box> = (1..10).map { Box(it) }
    private val historial: MutableList<RegistroAtencion> = mutableListOf()

    suspend fun registrarEntrada(paciente: Paciente): Boolean {
        val boxLibre = boxes.find { it.estado is Estado.Libre }

        if (boxLibre == null) {
            println("No hay boxes disponibles en este momento.")
            return false
        }

        boxLibre.estado = Estado.EnProceso("Registrando entrada")
        delay(3000)

        boxLibre.estado = Estado.EnAtencion(paciente)
        println("Entrada registrada: box ${boxLibre.numero}, paciente ${paciente.codigoAtencion}")
        return true
    }

    suspend fun registrarSalida(codigoAtencion: String, tiempoUsoMinutos: Int): Boolean {
        val box = boxes.find { estado ->
            val e = estado.estado
            e is Estado.EnAtencion && e.paciente.codigoAtencion == codigoAtencion
        }

        if (box == null) {
            println("No se encontró un paciente con ese código de atención en atención activa.")
            return false
        }

        val estadoActual = box.estado as Estado.EnAtencion
        val paciente = estadoActual.paciente

        box.estado = Estado.EnProceso("Calculando tarifa")
        delay(6500)

        val costoBase = paciente.calcularCosto(tiempoUsoMinutos)
        val costo = calcularMontoFinal(costoBase, paciente.tipoDueno)

        val ticket = historial.size + 1
        historial.add(RegistroAtencion(ticket, paciente, tiempoUsoMinutos, costo))

        box.estado = Estado.Libre
        println("Salida registrada: ticket $ticket, paciente ${paciente.codigoAtencion}, monto $costo")
        return true
    }
        private fun calcularMontoFinal(costoBase: Double, tipoDueno: TipoDueno): Double {
            val conIva = costoBase * 1.19
            return if (tipoDueno == TipoDueno.MUNICIPAL) {
                conIva * 0.5
            } else {
                conIva
            }
    }
}