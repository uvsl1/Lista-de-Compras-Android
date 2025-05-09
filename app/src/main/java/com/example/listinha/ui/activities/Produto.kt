package com.example.listinha.ui.activities

data class Produto(
    var nomeProduto: String,
    var quantidade: Int,
    var pego: Boolean = false,
    var precoUnitario: Double = 0.0
) {
    val precoTotal: Double
        get() = quantidade * precoUnitario
}
