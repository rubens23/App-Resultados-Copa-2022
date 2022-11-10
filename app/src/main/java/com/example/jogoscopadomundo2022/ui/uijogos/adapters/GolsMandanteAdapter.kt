package com.example.jogoscopadomundo2022.ui.uijogos.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jogoscopadomundo2022.databinding.GolsMandanteItemBinding
import com.example.jogoscopadomundo2022.domain.apijogos.AutorGolMandante

class GolsMandanteAdapter(val listaGolsMandante: ArrayList<AutorGolMandante>) : RecyclerView.Adapter<GolsMandanteAdapter.ViewHolder>() {
    class ViewHolder(val binding: GolsMandanteItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(golDoTimeMandante: AutorGolMandante){
            binding.tvGolMandante.text = "${golDoTimeMandante.nome_jogador} ${golDoTimeMandante.minuto_gol}'"
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = GolsMandanteItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(listaGolsMandante[position])

    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    override fun getItemCount(): Int = listaGolsMandante.size
}