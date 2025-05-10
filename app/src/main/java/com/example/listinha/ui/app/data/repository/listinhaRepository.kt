package com.example.listinha.ui.app.data.repository

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.listinha.ui.app.data.database.listinhaDbHelper
import com.example.listinha.ui.app.data.models.ListaCompras
import com.example.listinha.ui.app.data.models.Produto
import java.math.BigDecimal
import java.math.RoundingMode

class listinhaRepository(context: Context) {

    private val dbHelper = listinhaDbHelper(context)

    fun inserirLista(lista: ListaCompras): Long {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", lista.nome)
        }
        val idLista = db.insert("listaCompras", null, values)

        lista.produtos.forEach { produto ->
            val precoUnitario = produto.precoUnitario.setScale(2, RoundingMode.HALF_UP)
            val quantidade = produto.quantidade.setScale(2, RoundingMode.HALF_UP)
            val precoTotal = quantidade.multiply(precoUnitario).setScale(2, RoundingMode.HALF_UP)

            val produtoValues = ContentValues().apply {
                put("nome", produto.nome)
                put("quantidade", quantidade.toDouble())
                put("status", if (produto.status) 1 else 0)
                put("precoUnitario", precoUnitario.toDouble())
                put("precoTotal", precoTotal.toDouble())
                put("idListaCompras", idLista)
            }

            val idProduto = db.insert("produto", null, produtoValues)
            produto.id = idProduto.toInt()
        }

        return idLista
    }

    fun listarListas(): List<ListaCompras> {
        val db = dbHelper.readableDatabase
        val listas = mutableListOf<ListaCompras>()

        val cursor = db.rawQuery("SELECT * FROM listaCompras", null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow("id"))
            val nome = cursor.getString(cursor.getColumnIndexOrThrow("nome"))
            val lista = ListaCompras(id = id, nome = nome)

            val produtosCursor = db.rawQuery("SELECT * FROM produto WHERE idListaCompras = ?", arrayOf(id.toString()))
            while (produtosCursor.moveToNext()) {
                val idProduto = produtosCursor.getInt(produtosCursor.getColumnIndexOrThrow("id"))
                val nomeProduto = produtosCursor.getString(produtosCursor.getColumnIndexOrThrow("nome"))

                val quantidade = BigDecimal(produtosCursor.getDouble(produtosCursor.getColumnIndexOrThrow("quantidade")))
                    .setScale(2, RoundingMode.HALF_UP)

                val status = produtosCursor.getInt(produtosCursor.getColumnIndexOrThrow("status")) == 1

                val precoUnitario = BigDecimal(produtosCursor.getDouble(produtosCursor.getColumnIndexOrThrow("precoUnitario")))
                    .setScale(2, RoundingMode.HALF_UP)

                val produto = Produto(idProduto, nomeProduto, quantidade, status, precoUnitario)
                lista.produtos.add(produto)
            }
            produtosCursor.close()

            listas.add(lista)
        }
        cursor.close()

        return listas
    }

    fun atualizarLista(id: Int, novoNome: String) {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put("nome", novoNome)
        }
        db.update("listaCompras", values, "id = ?", arrayOf(id.toString()))
    }

    fun excluirLista(id: Int) {
        val db = dbHelper.writableDatabase
        db.delete("produto", "idListaCompras = ?", arrayOf(id.toString()))
        db.delete("listaCompras", "id = ?", arrayOf(id.toString()))
    }

    fun atualizarProduto(idProduto: Int, novaQuantidade: BigDecimal, novoPrecoUnitario: BigDecimal) {
        val db = dbHelper.writableDatabase

        val quantidade = novaQuantidade.setScale(2, RoundingMode.HALF_UP)
        val precoUnitario = novoPrecoUnitario.setScale(2, RoundingMode.HALF_UP)
        val precoTotal = quantidade.multiply(precoUnitario).setScale(2, RoundingMode.HALF_UP)

        val values = ContentValues().apply {
            put("quantidade", quantidade.toDouble())
            put("precoUnitario", precoUnitario.toDouble())
            put("precoTotal", precoTotal.toDouble())
        }

        db.update(
            "produto",
            values,
            "id = ?",
            arrayOf(idProduto.toString())
        )
    }

    fun excluirProduto(idProduto: Int) {
        val db = dbHelper.writableDatabase
        db.delete("produto", "id = ?", arrayOf(idProduto.toString()))
    }
}
