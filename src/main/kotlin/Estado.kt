sealed class Estado {
    object Libre : Estado()
    data class EnAtencion(val codigoAtencion: String) : Estado()
    data class EnProceso(val motivo: String) : Estado()
    data class FueraDeServicio(val motivo: String) : Estado()
}