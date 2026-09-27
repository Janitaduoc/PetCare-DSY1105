import kotlinx.coroutines.runBlocking

    fun main(): kotlin.Unit = runBlocking {
        val sistema = SistemaPetCare()

        val codigo = "CA12CD"

        if (!esCodigoAtencionValido(codigo)) {
            println("Código inválido")
        } else {
            val paciente = Canino(codigo, "Max", "Perro", "26-09-2026", TipoDueno.CONVENIO)
            sistema.registrarEntrada(paciente)
        }
    }