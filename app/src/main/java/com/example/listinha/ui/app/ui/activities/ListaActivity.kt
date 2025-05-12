package com.example.listinha.ui.app.ui.activities

import android.os.Bundle
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

                val adapter = ProdutoAdapter(this, it.produtos)
                binding.rvProdutos.layoutManager = LinearLayoutManager(this)
                binding.rvProdutos.adapter = adapter
            }
        }
    }
}
