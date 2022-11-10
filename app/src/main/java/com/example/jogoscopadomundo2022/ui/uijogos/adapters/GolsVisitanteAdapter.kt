package com.example.jogoscopadomundo2022.ui.uijogos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jogoscopadomundo2022.databinding.GolsVisitanteItemBinding
import com.example.jogoscopadomundo2022.domain.apijogos.AutorGolVisitante


class GolsVisitanteAdapter(val listaGolsVisitante: ArrayList<AutorGolVisitante>) : RecyclerView.Adapter<GolsVisitanteAdapter.ViewHolder>() {
    class ViewHolder(val binding: GolsVisitanteItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(golDoTimeVisitante: AutorGolVisitante){
            binding.tvGolVisitante.text = "${golDoTimeVisitante.nome_jogador} ${golDoTimeVisitante.minuto_gol}'"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GolsVisitanteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaGolsVisitante[position])

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = listaGolsVisitante.size


}