package com.example.listinha.ui.app.data.models

import java.math.BigDecimal
import java.math.RoundingMode

data class Produto(
    var id: Int? = null,
    var nome: String = "",
    var quantidade: BigDecimal = BigDecimal("0.0").setScale(2, RoundingMode.HALF_UP),
    var status: Boolean = false,
    var precoUnitario: BigDecimal = BigDecimal("0.0").setScale(2, RoundingMode.HALF_UP)
) {
    val precoTotal: BigDecimal
        get() = (quantidade * precoUnitario).setScale(2, RoundingMode.HALF_UP)

    override fun toString(): String {
        return "Produto(nome=$nome, quantidade=$quantidade, status=$status, precoUnitario=$precoUnitario, precoTotal=$precoTotal)"
    }

    fun alterarStatus() {
        if (status == false) {
            status = true
        } else {
            status = false
        }
    }
}
