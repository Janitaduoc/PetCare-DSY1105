import kotlinx.coroutines.delay

class SistemaPetCare {
    private val boxes: List<Box> = (1..10).map { Box(it) }
    private val historial: MutableList<RegistroAtencion> = mutableListOf()


    suspend fun registrarEntrada(paciente: Paciente): Boolean {
        val boxLibre = boxes.find { it.estado is Estado.Libre }

        if (boxLibre == null) {
            println("Sistema sin capacidad: no hay boxes disponibles en este momento.")
            return false
        }

        boxLibre.estado = Estado.EnProceso("Registrando entrada")
        delay(3000)

        boxLibre.estado = Estado.EnAtencion(paciente)
        println("Entrada registrada: box ${boxLibre.numero}, paciente ${paciente.codigoAtencion}")
        return true
    }


    suspend fun registrarSalida(codigoAtencion: String, tiempoUsoMinutos: Int): Boolean {
        val box = boxes.find { b ->
            val e = b.estado
            e is Estado.EnAtencion && e.paciente.codigoAtencion == codigoAtencion
        }

        if (box == null) {
            println("Paciente no encontrado: no hay un paciente con ese código en atención activa.")
            return false
        }

        val estadoActual = box.estado as Estado.EnAtencion
        val paciente = estadoActual.paciente

        box.estado = Estado.EnProceso("Calculando tarifa")
        delay(6500)

        val costoBase = paciente.calcularCosto(tiempoUsoMinutos)
        val costo = calcularMontoFinal(costoBase, paciente.tipoDueno)

        if (!esMontoValido(costo, paciente, tiempoUsoMinutos)) {
            println("Resultado de tarifa inválido: el monto calculado no es válido.")
            box.estado = Estado.EnAtencion(paciente)
            return false
        }


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

    fun recaudacionTotal(): Double {
        return historial.sumOf { it.montoPagado }
    }

    fun recaudacionPorTipo(): Map<String, Double> {
        return historial.groupBy { it.paciente::class.simpleName ?: "Desconocido" }
            .mapValues { (_, registros) -> registros.sumOf { it.montoPagado } }
    }

    fun boxesDisponibles(): Int {
        return boxes.count { it.estado is Estado.Libre }
    }

    fun pacientesConvenio(): List<Paciente> {
        return historial.filter { it.paciente.tipoDueno == TipoDueno.CONVENIO }
            .map { it.paciente }
    }

    fun ingresoPromedioPorPaciente(): Double {
        if (historial.isEmpty()) return 0.0
        return historial.map { it.montoPagado }.average()
    }

    fun codigosFinalizados(): List<String> {
        return historial.map { it.paciente.codigoAtencion }
    }

    fun pacienteConMasTiempoUso(): RegistroAtencion? {
        return historial.maxByOrNull { it.tiempoUsoMinutos }
    }

    fun reporteCierreTurno() {
        println("===== REPORTE DE CIERRE DE TURNO =====")
        historial.forEach { registro ->
            val tipo = registro.paciente::class.simpleName ?: "Desconocido"
            println("Ticket ${registro.numeroTicket} | $tipo | ${registro.paciente.codigoAtencion} | ${registro.tiempoUsoMinutos} min | $${registro.montoPagado}")
        }

        println("----------------------------------------")
        println("Total recaudado: $${recaudacionTotal()}")
        println("Pacientes atendidos: ${historial.size}")
        println("Ingreso promedio: $${ingresoPromedioPorPaciente()}")

        val tipoConMasIngresos = recaudacionPorTipo().maxByOrNull { it.value }
        println("Tipo con más ingresos: ${tipoConMasIngresos?.key ?: "N/A"}")

        println("Boxes disponibles al cierre: ${boxesDisponibles()}")
        println("========================================")
    }
}