package com.example.listinha.ui.app.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.listinha.R
import com.example.listinha.ui.app.data.models.*
import com.example.listinha.ui.app.data.repository.listinhaRepository
import java.math.BigDecimal


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("ciclo_vida", "onCreate")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        val repo = listinhaRepository(this)


        //EXEMPLO DE ROTINA SISTEMA

        //Lista de todas as listas de compras OBS.: Resgata do db caso tiver
        var listaGeral = repo.listarListas().toMutableList()

        //Listas de compras1
        val listaCompras1 = ListaCompras()

        //Produtos Lista1
        val produto11 = Produto(null,"Arroz", BigDecimal("1"),false, BigDecimal("9.99"))
        val produto12 = Produto(null,"Feijao", BigDecimal("2"),false,BigDecimal("5"))
        listaCompras1.nome = "Semana Páscoa"
        listaCompras1.produtos.add(produto11)
        listaCompras1.produtos.add(produto12)


        //Listas de compras2
        val listaCompras2 = ListaCompras()

        //Produtos Lista2
        val produto21 = Produto(null,"Coca-Cola", BigDecimal("3"),false, BigDecimal("10.0"))
        val produto22 = Produto(null,"Pepsi", BigDecimal(5),false, BigDecimal("3"))
        listaCompras2.nome = "Semana Natal"
        listaCompras2.produtos.add(produto21)
        listaCompras2.produtos.add(produto22)

        /*
        Trecho abaixo irá inserir as listas de compras 1 & 2
        toda vez que rodar o projeto
        */
        repo.inserirLista(listaCompras1)
        repo.inserirLista(listaCompras2)

        listaGeral.forEach { l ->
            println("ID Lista: ${l.id} - Nome Lista: ${l.nome}")
            l.produtos.forEach {
                println("ID Produto: ${it.id} - Produto: ${it.nome} - " +
                        "Quantidade: ${it.quantidade} - Status: ${it.status} - " +
                        "PreçoUnitario: ${it.precoUnitario} - PreçoTotal: ${it.precoTotal}")
            }
            println("\n\n")
        }

    }



    override fun onStart() {
        super.onStart()
        Log.i("ciclo_vida", "onStart")

    }



    override fun onResume() {
        super.onResume()
        Log.i("ciclo_vida", "onResume")

    }



    override fun onPause() {
        super.onPause()
        Log.i("ciclo_vida", "onPause")

    }



    override fun onStop() {
        super.onStop()
        Log.i("ciclo_vida", "onStop")

    }



    override fun onRestart() {
        super.onRestart()
        Log.i("ciclo_vida", "onRestart")

    }



    override fun onDestroy() {
        Log.i("ciclo_vida", "onDestroy")
        super.onDestroy()

    }
}