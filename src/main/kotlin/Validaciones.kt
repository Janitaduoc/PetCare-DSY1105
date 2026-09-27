// Valida que el código de atención cumpla el formato del glosario: 2 letras + 2 dígitos + 2 letras.
fun esCodigoAtencionValido(codigo: String): Boolean {
    val patron = Regex("^[A-Za-z]{2}[0-9]{2}[A-Za-z]{2}$")
    return patron.matches(codigo)
}

// Un monto negativo siempre es error. Un monto igual a cero solo es válido
// en el caso de Felino con menos de 20 minutos de atención.
fun esMontoValido(monto: Double, paciente: Paciente, tiempoUsoMinutos: Int): Boolean {
    if (monto < 0) {
        return false
    }
    if (monto == 0.0) {
        return paciente is Felino && tiempoUsoMinutos < 20
    }
    return true
}