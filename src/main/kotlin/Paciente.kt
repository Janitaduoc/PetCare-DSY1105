abstract class Paciente(
    val codigoAtencion: String,
    val nombre: String,
    val especie: String,
    val fechaIngreso: String, // o un tipo de fecha/hora, según lo que definas
    val tipoDueno: TipoDueno
) {
    abstract fun calcularCosto(minutosUso: Int): Double
}