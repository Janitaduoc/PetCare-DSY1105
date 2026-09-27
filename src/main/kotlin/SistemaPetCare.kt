class SistemaPetCare {
    private val boxes: List<Box> = (1..10).map { Box(it) }
    private val historial: MutableList<RegistroAtencion> = mutableListOf()
}