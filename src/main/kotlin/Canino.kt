class Canino(
    codigoAtencion: String,
    nombreMascota: String,
    especie: String,
    fechaIngreso: String,
    tipoDueno: TipoDueno
) : Paciente(codigoAtencion, nombreMascota, especie, fechaIngreso, tipoDueno) {

    private val tarifaBase = 12000.0 // $12.000/hr

    override fun calcularCosto(minutosUso: Int): Double {
        val horas = minutosUso / 60.0
        var costo = tarifaBase * horas
        if (tipoDueno == TipoDueno.CONVENIO) {
            costo *= 0.8 // descuento del 20%
        }
        return costo
    }
}