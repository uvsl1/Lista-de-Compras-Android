package com.example.listinha.ui.app.data.models

import java.math.BigDecimal
import java.math.RoundingMode

data class ListaCompras(
    var id: Int? = null,
    var nome: String = "",
    var produtos: MutableList<Produto> = mutableListOf()
) {
    val totalCarrinho: BigDecimal
        get() = produtos.sumOf { it.precoTotal }
            .setScale(2, RoundingMode.HALF_UP)
}
