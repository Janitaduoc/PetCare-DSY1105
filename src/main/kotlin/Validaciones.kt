fun esCodigoAtencionValido(codigo: String): Boolean {
    val patron = Regex("^[A-Za-z]{2}[0-9]{2}[A-Za-z]{2}$")
    return patron.matches(codigo)


}