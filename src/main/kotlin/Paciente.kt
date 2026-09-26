abstract class Paciente(
    val codigoAtencion: String,
    val nombreMascota: String,
    val especie: String,
    val fechaIngreso: String,
    val tipoDueno: TipoDueno
) {
    abstract fun calcularCosto(minutosUso: Int): Double
}