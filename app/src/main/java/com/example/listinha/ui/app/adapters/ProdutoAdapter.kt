package com.example.listinha.ui.app.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.listinha.databinding.ItemProdutoBinding
import com.example.listinha.ui.app.data.models.Produto
import com.example.listinha.ui.app.data.repository.ListinhaRepository

class ProdutoAdapter(
    private val context: Context,
    private var produtos: MutableList<Produto>
) : RecyclerView.Adapter<ProdutoAdapter.ViewHolder>() {

    private var recyclerView: RecyclerView? = null

    inner class ViewHolder(private val binding: ItemProdutoBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(produto: Produto) {
            binding.nomeProduto.text = "Nome do produto: ${produto.nome}"
            binding.quantidadeProduto.text = "Qtd: ${produto.quantidade}"
            binding.precoProduto.text = "Preço Un ou Kg: R$ ${produto.precoUnitario}"
            binding.totalProduto.text = "Total: R$ ${produto.precoTotal}"

            binding.checkProduto.setOnCheckedChangeListener(null)
            binding.checkProduto.isChecked = produto.status

            binding.checkProduto.setOnCheckedChangeListener { _, isChecked ->
                produto.status = isChecked
                produto.id?.let { id ->
                    val repo = ListinhaRepository(context)
                    repo.atualizarStatusProduto(id, isChecked)
                }
                ordenarProdutosComMovimento()
            }

            binding.btnEditar.setOnClickListener {
                abrirDialogoEdicao(produto)
            }
        }
    }

    private fun abrirDialogoEdicao(produto: Produto) {
        val layout = LinearLayout(context).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(40, 30, 40, 10)
        }

        val inputNome = EditText(context).apply {
            hint = "Nome do produto"
            setText(produto.nome)
        }

        val inputQtd = EditText(context).apply {
            hint = "Quantidade"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
            setText(produto.quantidade.toPlainString())
        }

        val inputPreco = EditText(context).apply {
            hint = "Preço Unitário"
            inputType = android.text.InputType.TYPE_CLASS_NUMBER or android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL
            setText(produto.precoUnitario.toPlainString())
        }

        layout.addView(inputNome)
        layout.addView(inputQtd)
        layout.addView(inputPreco)

        AlertDialog.Builder(context)
            .setTitle("Editar Produto")
            .setView(layout)
            .setPositiveButton("Salvar") { _, _ ->
                val novoNome = inputNome.text.toString().ifBlank { produto.nome }
                val novaQtd = inputQtd.text.toString().toBigDecimalOrNull()?.setScale(2)
                val novoPreco = inputPreco.text.toString().toBigDecimalOrNull()?.setScale(2)

                val repo = ListinhaRepository(context)

                produto.nome = novoNome
                if (novaQtd != null) produto.quantidade = novaQtd
                if (novoPreco != null) produto.precoUnitario = novoPreco

                produto.id?.let {
                    if (novaQtd != null && novoPreco != null) {
                        repo.atualizarProduto(it, novaQtd, novoPreco)
                    }
                }

                notifyDataSetChanged()
                ordenarProdutosComMovimento()
            }
            .setNegativeButton("Cancelar", null)
            .show()
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

    override fun onAttachedToRecyclerView(rv: RecyclerView) {
        super.onAttachedToRecyclerView(rv)
        recyclerView = rv
    }

    override fun onDetachedFromRecyclerView(rv: RecyclerView) {
        super.onDetachedFromRecyclerView(rv)
        recyclerView = null
    }

    private fun ordenarProdutosComMovimento() {
        val novaLista = produtos.sortedWith(compareBy({ it.status }, { it.nome }))
        val copiaAntiga = produtos.toList()

        produtos.clear()
        produtos.addAll(novaLista)

        recyclerView?.post {
            for (i in copiaAntiga.indices) {
                val antigo = copiaAntiga[i]
                val novoIndex = produtos.indexOf(antigo)
                if (i != novoIndex) {
                    notifyItemMoved(i, novoIndex)
                }
            }
            notifyDataSetChanged()
        }
    }

    fun adicionarProduto(produto: Produto) {
        produtos.add(produto)
        ordenarProdutosComMovimento()
    }
}
