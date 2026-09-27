class Felino(
    codigoAtencion: String,
    nombreMascota: String,
    especie: String,
    fechaIngreso: String,
    tipoDueno: TipoDueno
) : Paciente(codigoAtencion, nombreMascota, especie, fechaIngreso, tipoDueno) {

    private val tarifaBase = 9000.0

    // Tarifa base por hora. Si el tiempo de atención es menor a 20 minutos,
    // el cobro es $0 sin importar el tipo de dueño (regla especial de negocio).
    override fun calcularCosto(minutosUso: Int): Double {
        val horas = minutosUso / 60.0
        var costo = tarifaBase * horas

        if (minutosUso < 20) {
            costo = 0.0
        }

        return costo
    }
}