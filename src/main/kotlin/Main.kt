import kotlinx.coroutines.runBlocking

fun main() = runBlocking {
    val sistema = SistemaPetCare()
    var continuar = true

    while (continuar) {
        println("\n===== PetCare =====")
        println("1. Registrar entrada")
        println("2. Registrar salida")
        println("3. Boxes disponibles")
        println("4. Pacientes convenio")
        println("5. Ingreso promedio")
        println("6. Códigos finalizados")
        println("7. Paciente con más tiempo de uso")
        println("8. Cerrar turno (reporte final)")
        println("9. Salir")
        print("Elige una opción: ")

        when (readLine()?.trim()) {
            "1" -> {
                print("Tipo (1=Canino, 2=Felino, 3=Exotico): ")
                val tipo = readLine()?.trim()
                print("Código de atención: ")
                val codigo = readLine()?.trim() ?: ""

                if (!esCodigoAtencionValido(codigo)) {
                    println("Código de atención inválido.")
                } else {
                    print("Nombre de la mascota: ")
                    val nombre = readLine()?.trim() ?: ""
                    print("Especie: ")
                    val especie = readLine()?.trim() ?: ""
                    print("Tipo de dueño (1=Particular, 2=Convenio, 3=Municipal): ")
                    val tipoDuenoOpcion = readLine()?.trim()
                    val tipoDueno = when (tipoDuenoOpcion) {
                        "1" -> TipoDueno.PARTICULAR
                        "2" -> TipoDueno.CONVENIO
                        "3" -> TipoDueno.MUNICIPAL
                        else -> TipoDueno.PARTICULAR
                    }
                    val fecha = "26-09-2026"

                    val paciente = when (tipo) {
                        "1" -> Canino(codigo, nombre, especie, fecha, tipoDueno)
                        "2" -> Felino(codigo, nombre, especie, fecha, tipoDueno)
                        "3" -> {
                            print("¿Es silvestre? (s/n): ")
                            val esSilvestre = readLine()?.trim()?.lowercase() == "s"
                            Exotico(codigo, nombre, especie, fecha, tipoDueno, esSilvestre)
                        }
                        else -> null
                    }

                    if (paciente == null) {
                        println("Tipo de paciente inválido.")
                    } else {
                        sistema.registrarEntrada(paciente)
                    }
                }
            }
            "2" -> {
                print("Código de atención: ")
                val codigo = readLine()?.trim() ?: ""
                print("Tiempo de uso (minutos): ")
                val tiempo = readLine()?.trim()?.toIntOrNull()

                if (tiempo == null) {
                    println("Tiempo inválido.")
                } else {
                    sistema.registrarSalida(codigo, tiempo)
                }
            }
            "3" -> println("Boxes disponibles: ${sistema.boxesDisponibles()}")
            "4" -> {
                val convenio = sistema.pacientesConvenio()
                println("Pacientes convenio: ${convenio.map { it.codigoAtencion }}")
            }
            "5" -> println("Ingreso promedio: $${sistema.ingresoPromedioPorPaciente()}")
            "6" -> println("Códigos finalizados: ${sistema.codigosFinalizados()}")
            "7" -> {
                val masTiempo = sistema.pacienteConMasTiempoUso()
                if (masTiempo == null) {
                    println("Aún no hay pacientes atendidos.")
                } else {
                    println("Paciente con más tiempo: ${masTiempo.paciente.codigoAtencion} (${masTiempo.tiempoUsoMinutos} min)")
                }
            }
            "8" -> sistema.reporteCierreTurno()
            "9" -> {
                println("Cerrando PetCare...")
                continuar = false
            }
            else -> println("Opción inválida.")
        }
    }
}