package com.example.listinha.ui.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listinha.databinding.ItemProdutoBinding
import com.example.listinha.ui.app.data.models.Produto

class ProdutoAdapter(
    private val context: Context,
    private val produtos: List<Produto>
) : RecyclerView.Adapter<ProdutoAdapter.ViewHolder>() {

    class ViewHolder(private val binding: ItemProdutoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(produto: Produto) {
            binding.nomeProduto.text = produto.nome
            binding.quantidadeProduto.text = "Qtd: ${produto.quantidade}"
            binding.precoProduto.text = "Unit: R$ ${produto.precoUnitario}"
            binding.totalProduto.text = "Total: R$ ${produto.precoTotal}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ItemProdutoBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(produtos[position])
    }

    override fun getItemCount(): Int = produtos.size
}
