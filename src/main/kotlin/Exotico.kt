class Exotico (
    codigoAtencion: String,
    nombreMascota: String,
    especie: String,
    fechaIngreso: String,
    tipoDueno: TipoDueno,

    val esSilvestre: Boolean
): Paciente(codigoAtencion, nombreMascota, especie, fechaIngreso, tipoDueno){

    private val tarifaBase = 20000.0

    // Tarifa base por hora, con recargo del 30% si el paciente es un animal silvestre.
    override fun calcularCosto(minutosUso: Int): Double {
        val horas = minutosUso / 60.0
        var costo = tarifaBase * horas
        if (esSilvestre){
            costo *= 1.3
        }
        return costo
    }
}