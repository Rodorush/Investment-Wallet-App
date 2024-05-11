data class Ativo(
    val id: Long, // Identificador único do ativo
    val sigla: String, // Sigla do ativo (por exemplo, PETR4, VALE3)
    val nome: String, // Nome completo do ativo (por exemplo, Petrobras, Vale)
    val mercado: String // Mercado em que o ativo é negociado (por exemplo, B3, NASDAQ)
)
