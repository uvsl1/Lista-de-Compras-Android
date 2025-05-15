package com.example.listinha.ui.app.ui.activities

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listinha.R
import com.example.listinha.databinding.ActivityListaBinding
import com.example.listinha.ui.app.adapters.ProdutoAdapter
import com.example.listinha.ui.app.data.models.Produto
import com.example.listinha.ui.app.data.repository.ListinhaRepository
import java.math.BigDecimal
import java.math.RoundingMode

class ListaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaBinding
    private var idLista: Int = -1
    private lateinit var adapter: ProdutoAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idLista = intent.getIntExtra("idLista", -1)

        if (idLista != -1) {
            val repo = ListinhaRepository(this)
            val lista = repo.listarListas().find { it.id == idLista }

            lista?.let {
                binding.nomeLista.text = it.nome

                adapter = ProdutoAdapter(this, it.produtos.toMutableList()) {
                    atualizarValorTotal()
                }
                binding.rvProdutos.layoutManager = LinearLayoutManager(this)
                binding.rvProdutos.adapter = adapter

                atualizarValorTotal()

                binding.btnEditar.setOnClickListener {
                    val editText = EditText(this).apply {
                        setText(binding.nomeLista.text.toString())
                    }

                    AlertDialog.Builder(this)
                        .setTitle("Editar Nome da Lista")
                        .setView(editText)
                        .setPositiveButton("Salvar") { _, _ ->
                            val novoNome = editText.text.toString().trim()
                            if (novoNome.isNotEmpty()) {
                                binding.nomeLista.text = novoNome
                                repo.atualizarLista(lista.id!!, novoNome)
                            }
                        }
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show()
                }

                binding.fabAdicionarProduto.setOnClickListener {
                    val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_produto, null)
                    val etNome = dialogView.findViewById<EditText>(R.id.etNome)
                    val etQtd = dialogView.findViewById<EditText>(R.id.etQuantidade)
                    val etPreco = dialogView.findViewById<EditText>(R.id.etPreco)

                    AlertDialog.Builder(this)
                        .setTitle("Adicionar Produto")
                        .setView(dialogView)
                        .setPositiveButton("Salvar") { _, _ ->
                            val nome = etNome.text.toString().trim()
                            val quantidade = etQtd.text.toString().toDoubleOrNull()
                            val precoUnitario = etPreco.text.toString().toDoubleOrNull() ?: 0.0

                            if (nome.isNotEmpty() && quantidade != null) {
                                val novoProduto = Produto(
                                    nome = nome,
                                    quantidade = BigDecimal(quantidade).setScale(2, RoundingMode.HALF_UP),
                                    precoUnitario = BigDecimal(precoUnitario).setScale(2, RoundingMode.HALF_UP),
                                    status = false,
                                    listaId = idLista
                                )

                                val idInserido = repo.inserirProduto(novoProduto)
                                novoProduto.id = idInserido.toInt()
                                adapter.adicionarProduto(novoProduto)
                                atualizarValorTotal()
                            } else {
                                Toast.makeText(this, "Preencha o nome e a quantidade corretamente", Toast.LENGTH_SHORT).show()
                            }
                        }
                        .setNegativeButton("Cancelar", null)
                        .create()
                        .show()
                }

            }
        }
    }

    private fun atualizarValorTotal() {
        val total = adapter.produtos.filter { it.status }.sumOf { it.precoTotal }.setScale(2, RoundingMode.HALF_UP)
        binding.txtValorTotal.text = "Total: R$ ${total}"
    }
}