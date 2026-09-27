sealed class Estado {
    object Libre : Estado()
    data class EnAtencion(val paciente: Paciente) : Estado()
    data class EnProceso(val motivo: String) : Estado()
    data class FueraDeServicio(val motivo: String) : Estado()
}