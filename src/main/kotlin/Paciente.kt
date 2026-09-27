abstract class Paciente(
    val codigoAtencion: String,
    val nombre: String,
    val especie: String,
    val fechaIngreso: String, // o un tipo de fecha/hora, según lo que definas
    val tipoDueno: TipoDueno
) {
    // Cada tipo de paciente (Canino, Felino, Exotico) implementa su propia regla de cobro.
    // No incluye IVA ni descuento municipal: esas reglas son generales y se aplican después,
    // en SistemaPetCare.calcularMontoFinal().
    abstract fun calcularCosto(minutosUso: Int): Double
}