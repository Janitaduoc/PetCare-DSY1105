// Modela los 4 estados posibles de un box. Al ser "sealed", el compilador obliga
// a manejar todos los casos en cualquier "when" que use Estado, evitando estados no contemplados.
sealed class Estado {
    object Libre : Estado()
    data class EnAtencion(val paciente: Paciente) : Estado()
    data class EnProceso(val motivo: String) : Estado()
    data class FueraDeServicio(val motivo: String) : Estado()
}