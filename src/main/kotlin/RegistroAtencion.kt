data class RegistroAtencion(
    val numeroTicket: Int,
    val paciente: Paciente,
    val tiempoUsoMinutos: Int,
    val montoPagado: Double
)
fun esMontoValido(monto: Double, paciente: Paciente, tiempoUsoMinutos: Int): Boolean {
    if (monto < 0) {
        return false
    }
    if (monto == 0.0) {
        return paciente is Felino && tiempoUsoMinutos < 20
    }
    return true
}