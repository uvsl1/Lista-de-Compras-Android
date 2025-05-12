package com.example.listinha.ui.app.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listinha.databinding.ActivityMainBinding
import com.example.listinha.ui.app.adapters.ItemListaAdapter
import com.example.listinha.ui.app.data.models.*
import com.example.listinha.ui.app.data.repository.ListinhaRepository
import java.math.BigDecimal

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ItemListaAdapter
    private lateinit var repo: ListinhaRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repo = ListinhaRepository(this)

        var listaGeral = repo.listarListas().toMutableList()

        if (listaGeral.isEmpty()) {
            val listaCompras1 = ListaCompras(
                nome = "Semana Páscoa",
                produtos = mutableListOf(
                    Produto(null, "Arroz", BigDecimal("1"), false, BigDecimal("9")),
                    Produto(null, "Feijão", BigDecimal("2"), false, BigDecimal("5"))
                )
            )

            val listaCompras2 = ListaCompras(
                nome = "Semana Natal",
                produtos = mutableListOf(
                    Produto(null, "Coca-Cola", BigDecimal("3"), false, BigDecimal("10")),
                    Produto(null, "Pepsi", BigDecimal("5"), false, BigDecimal("3"))
                )
            )

            repo.inserirLista(listaCompras1)
            repo.inserirLista(listaCompras2)

            listaGeral = repo.listarListas().toMutableList()
        }

        adapter = ItemListaAdapter(this, listaGeral) { lista ->
            // Exibe o pop-up de confirmação ao invés de excluir diretamente
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Confirmar Exclusão")
            builder.setMessage("Você tem certeza que deseja excluir essa lista?")

            builder.setPositiveButton("Sim") { _, _ ->
                // Exclui a lista caso o usuário confirme
                repo.excluirLista(lista.id!!)
                val listasAtualizadas = repo.listarListas().toMutableList()
                adapter.atualizarDados(listasAtualizadas)
            }

            builder.setNegativeButton("Não", null)
            builder.show()
        }

        binding.recyclerview.layoutManager = LinearLayoutManager(this)
        binding.recyclerview.adapter = adapter

        binding.fabAdicionarLista.setOnClickListener {
            val builder = android.app.AlertDialog.Builder(this)
            builder.setTitle("Nova Lista de Compras")

            val input = android.widget.EditText(this)
            input.hint = "Nome da lista"
            builder.setView(input)

            builder.setPositiveButton("Criar") { _, _ ->
                val nomeLista = input.text.toString().trim()

                if (nomeLista.isNotEmpty()) {
                    val novaLista = ListaCompras(nome = nomeLista)
                    repo.inserirLista(novaLista)

                    val listasAtualizadas = repo.listarListas().toMutableList()
                    adapter.atualizarDados(listasAtualizadas)
                } else {
                    android.widget.Toast.makeText(this, "Nome não pode ser vazio", android.widget.Toast.LENGTH_SHORT).show()
                }
            }

            builder.setNegativeButton("Cancelar", null)
            builder.show()
        }
    }

    override fun onRestart() {
        super.onRestart()
        val listasAtualizadas = repo.listarListas().toMutableList()
        adapter.atualizarDados(listasAtualizadas)
    }
}
