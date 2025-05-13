package com.example.listinha.ui.app.ui.activities

import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listinha.databinding.ActivityListaBinding
import com.example.listinha.ui.app.adapters.ProdutoAdapter
import com.example.listinha.ui.app.data.repository.ListinhaRepository

class ListaActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val idLista = intent.getIntExtra("idLista", -1)

        if (idLista != -1) {
            val repo = ListinhaRepository(this)
            val lista = repo.listarListas().find { it.id == idLista }

            lista?.let {
                binding.nomeLista.text = it.nome

                val adapter = ProdutoAdapter(this, it.produtos.toMutableList())
                binding.rvProdutos.layoutManager = LinearLayoutManager(this)
                binding.rvProdutos.adapter = adapter

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
            }
        }
    }
}
