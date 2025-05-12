package com.example.listinha.ui.app.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.listinha.databinding.ListaItemBinding
import com.example.listinha.ui.app.data.models.ListaCompras
import com.example.listinha.ui.app.ui.activities.ListaActivity

class ItemListaAdapter(
    private val context: Context,
    private val listas: MutableList<ListaCompras>,
    private val onExcluirClick: (ListaCompras) -> Unit
) : RecyclerView.Adapter<ItemListaAdapter.ViewHolder>() {

    inner class ViewHolder(
        private val binding: ListaItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(lista: ListaCompras) {
            binding.itemLista.text = lista.nome

            binding.root.setOnClickListener {
                val intent = Intent(context, ListaActivity::class.java)
                intent.putExtra("idLista", lista.id)
                context.startActivity(intent)
            }

            binding.btnExcluir.setOnClickListener {
                onExcluirClick(lista)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = ListaItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val lista = listas[position]
        holder.bind(lista)
    }

    override fun getItemCount(): Int = listas.size

    fun atualizarDados(novasListas: List<ListaCompras>) {
        listas.clear()
        listas.addAll(novasListas)
        notifyDataSetChanged()
    }
}
